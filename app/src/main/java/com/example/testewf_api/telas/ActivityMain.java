package com.example.testewf_api.telas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.testewf_api.R;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener
{
    // Controles da tela
    private Button btnIniciar = null;
    private Toolbar toolbar = null;

    // Variaveis da classe

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Chama o metodo que inicia os controles e carrega os dados
            iniciaControles();
        }
        catch (Exception err)
        {
            Toast.makeText(this, "Erro onCreate Tela ActivityMain: " + err.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * Inicia os controles da tela e carrega os dados
     * */
    private void iniciaControles() throws Exception
    {
        // Obtem as referencias do xml
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Configura a toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Define o listener
        btnIniciar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        try
        {
            if( view == btnIniciar )
            {
                // Chama o metodo para abrir a tela seguinte
                abreTelaRepositorios();
            }
        }
        catch (Exception err)
        {
            Toast.makeText(this, "Erro onClick Tela ActivityMain: " + err.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    private void abreTelaRepositorios() throws Exception
    {
        Intent intent = null;

        // Inicializa a intent e abre a tela
        intent = new Intent(this, ActivityRepositorios.class);
        startActivity(intent);
    }
}
