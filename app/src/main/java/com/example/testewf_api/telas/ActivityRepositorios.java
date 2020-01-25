package com.example.testewf_api.telas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testewf_api.R;
import com.example.testewf_api.adapter.AdapterRepositorios;
import com.example.testewf_api.model.GitHubItems;
import com.example.testewf_api.model.GitHubRepositorios;
import com.example.testewf_api.rest.APIClient;
import com.example.testewf_api.rest.GitHubRepoEndPoint;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRepositorios extends AppCompatActivity implements View.OnClickListener
{
    // Controles da classe
    private RecyclerView rcvRepositorios = null;
    private Button btnBuscar = null;
    private Button btnMostrar = null;
    private EditText txtBuscar = null;
    private Toolbar toolbar = null;

    // Variaveis da classe
    private AdapterRepositorios adapterRepositorios = null;
    private ArrayList<GitHubRepositorios> arrRepositorios = new ArrayList<>();
    private ArrayList<GitHubItems> arrItens = new ArrayList<>();
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_repositorios);

            // Chama o metodo que inicia os controles e carrega os dados
            iniciaControles();
        }
        catch (Exception err)
        {
            Toast.makeText(this, "Erro onCreate Tela ActivityRepositorios: " + err.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    /**
     * Inicia os controles da tela e carrega os dados
     * */
    private void iniciaControles() throws Exception
    {
        // Obtem as referencias do xml
        rcvRepositorios = (RecyclerView) findViewById(R.id.rcvRepositorios);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnMostrar = (Button) findViewById(R.id.btnMostrarTudo);
        txtBuscar = (EditText) findViewById(R.id.txtBuscar);

        // Define o listener do botao
        btnBuscar.setOnClickListener(this);
        btnMostrar.setOnClickListener(this);

        // Inicializa o layout do recycler
        rcvRepositorios.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false));
        rcvRepositorios.setHasFixedSize(true);

        // Instancia o adapter e define no recycler
        adapterRepositorios = new AdapterRepositorios(arrItens, this, this);
        rcvRepositorios.setAdapter(adapterRepositorios);

        // Configura a toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.voltar);

        // Obtem os repostiorios e carrega na lista
        carregaRepositorios();
    }

    private void carregaRepositorios()
    {
        GitHubRepoEndPoint apiService = APIClient.getClient().create(GitHubRepoEndPoint.class);

        Call<GitHubRepositorios> call = apiService.getRepo();

        // Configura e apresenta o progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Aguarde...");
        progressDialog.setMessage("Obtendo os dados.");
        progressDialog.show();

        call.enqueue(new Callback<GitHubRepositorios>()
        {
            @Override
            public void onResponse(Call<GitHubRepositorios> call, Response<GitHubRepositorios> response)
            {
                // Limpa a lista e adiciona os dados obtidos do servidor
                arrRepositorios.clear();
                arrRepositorios.add(response.body());

                // Loop pelos dados obtidos
                for( GitHubRepositorios gitHubRepositorios : arrRepositorios )
                {
                    // Loop pelo array dos itens
                    for( GitHubItems gitHubItems : gitHubRepositorios.arrItens )
                    {
                        arrItens.add(gitHubItems);
                    }
                }

                // Atualiza a lista e fecha o progress dialog
                adapterRepositorios.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GitHubRepositorios> call, Throwable t)
            {
                // Apresenta o erro no log
                Log.e("Repos", t.toString());


                progressDialog.dismiss();
            }
        });
    }

    /**
     * metodo que abre a tela de detalhes ao clicar no item da lista (chamado no adapter)
     * */
    public void abreTelaDetalhes(GitHubItems gitHubItems)
    {
        Intent intent = null;

        try
        {
            // Instancia a intent e abre a tela
            intent = new Intent(this, ActivityDetalhes.class);
            intent.putExtra("gitHubItems", gitHubItems);
            startActivity(intent);
        }
        catch (Exception err)
        {
            Toast.makeText(this, "Erro ActivityRepositorios metodo abreTelaDetalhes: " + err.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Faz a buscaAppPeloNome pelo que o usuario digitou
     * */
    public void buscaAppPeloNome() throws Exception
    {
        String sBuscarAux = "";
        ArrayList<GitHubItems> arrItensAux = new ArrayList<>();

        sBuscarAux = txtBuscar.getText().toString().trim();

        // Se o usuario digitou algum filtro
        if( !sBuscarAux.equals("") )
        {
            // Loop pelo array de itens
            for(GitHubItems gitHubItems : arrItens )
            {
                // Se conter o nome digitado adiciona na array auxiliar
                if(gitHubItems.sName.toUpperCase().contains(sBuscarAux.toUpperCase()) || gitHubItems.sName.equalsIgnoreCase(sBuscarAux))
                {
                    arrItensAux.add(gitHubItems);
                }
            }

            // Se o array estiver vazio, apresenta mensagem
            if( arrItensAux.size() == 0 )
            {
                Toast.makeText(this, "Nenhum app encontrado. Verifique o nome digitado e tente novamente.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Instancia o adapter e define no recycler
                adapterRepositorios = new AdapterRepositorios(arrItensAux, this, this);
                rcvRepositorios.setAdapter(adapterRepositorios);
                adapterRepositorios.notifyDataSetChanged();
            }
        }
        else
        {
            Toast.makeText(this, "Digite o nome para pesquisa.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Atualiza a lista com todos os itens obtidos
     * */
    private void mostrarTudo() throws Exception
    {
        // Instancia o adapter e define no recycler
        adapterRepositorios = new AdapterRepositorios(arrItens, this, this);
        rcvRepositorios.setAdapter(adapterRepositorios);
        adapterRepositorios.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view)
    {
        try
        {
            // Se clicou no botao para pesquisar pelo nome
            if( view == btnBuscar )
            {
                // Chama o metodo que apresenta na tela os apps com o nome digitado
                buscaAppPeloNome();

                // Limpa a caixa de texto
                txtBuscar.setText("");
            }
            // Se clicou no botao para mostrar a lista
            else if( view == btnMostrar )
            {
                // Chama o metodo que apresenta a lista completa
                mostrarTudo();

                // Limpa a caixa de texto
                txtBuscar.setText("");
            }
        }
        catch (Exception err)
        {
            Toast.makeText(this, "Erro ActivityRepositorios metodo onClick: " + err.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
