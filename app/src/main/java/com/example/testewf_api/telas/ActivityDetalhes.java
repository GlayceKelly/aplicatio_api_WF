package com.example.testewf_api.telas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.testewf_api.R;
import com.example.testewf_api.adapter.AdapterRepositorios;
import com.example.testewf_api.model.GitHubItems;

public class ActivityDetalhes extends AppCompatActivity
{
    // Controles da classe
    private ImageView imgAvatarDetalhes = null;
    private TextView lblLinguagem = null;
    private TextView lblDescricao = null;
    private Toolbar toolbar = null;

    // Variaveis da classe
    private GitHubItems gitHubItems = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detalhes);

            // Chama o metodo que inicia os controles e carrega os dados
            iniciaControles();
        }
        catch (Exception err)
        {
            Toast.makeText(this, "Erro onCrete ActivityDetalhes " + err.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Inicia os controles da tela e carrega os dados
     * */
    private void iniciaControles() throws Exception
    {
        String sUrlFoto = "";

        // Obtendo as referencias do xml
        imgAvatarDetalhes = (ImageView) findViewById(R.id.imgAvatarDetalhes);
        lblLinguagem = (TextView) findViewById(R.id.lblLinguagem);
        lblDescricao = (TextView) findViewById(R.id.lblDescricao);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Configura a toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.voltar);

        // Obtem o objeto da tela anterior
        gitHubItems = (GitHubItems) getIntent().getSerializableExtra("gitHubItems");

        // Obtem a url da foto
        sUrlFoto = gitHubItems.gitHubOwner.sAvatarUrl;

        // Define a imagem obtida na image view
        Glide.with(this).load(sUrlFoto).centerCrop().into(imgAvatarDetalhes);

        // Define nas labels a liguagem e a descricao
        lblLinguagem.setText(gitHubItems.sLanguage);
        lblDescricao.setText(gitHubItems.sDescription);
    }
}
