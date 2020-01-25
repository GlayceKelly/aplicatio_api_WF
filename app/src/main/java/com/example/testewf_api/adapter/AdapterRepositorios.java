package com.example.testewf_api.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.testewf_api.R;
import com.example.testewf_api.model.GitHubItems;
import com.example.testewf_api.model.GitHubRepositorios;
import com.example.testewf_api.telas.ActivityRepositorios;

import java.util.ArrayList;

public class AdapterRepositorios extends RecyclerView.Adapter<AdapterRepositorios.RespositoriosViewHolder> implements View.OnClickListener
{
    // Variaveis da classe
    private ArrayList<GitHubItems> arrItens = null;
    private Context context = null;
    private ActivityRepositorios activityRepositorios = null;

    // Construtor da classe
    public AdapterRepositorios(ArrayList<GitHubItems> arrItensParam, Context contextParam, ActivityRepositorios activityRepositoriosParam)
    {
        arrItens = arrItensParam;
        context = contextParam;
        activityRepositorios = activityRepositoriosParam;
    }

    @NonNull
    @Override
    public RespositoriosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {
        // Infla o layout da lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lst_repositorios, null, false);
        return new RespositoriosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RespositoriosViewHolder holder, int iPosition)
    {
        String sUrlFoto = "";

        try
        {
            // Define nas labels os dados conforme o indice
            holder.lblRepoName.setText(arrItens.get(iPosition).sName);
            holder.lblRepoLanguage.setText(arrItens.get(iPosition).sLanguage);

            // Definindo na string a url da imagem
            sUrlFoto = arrItens.get(iPosition).gitHubOwner.sAvatarUrl;

            // Define a imagem obtida na image view
            Glide.with(context).load(sUrlFoto).centerCrop().into(holder.imgAvatar);

            // Define o listener para o clique e as tags para obter o holder e a posicao
            holder.lblDetalhes.setOnClickListener(this);
            holder.lblDetalhes.setTag(R.string.tag1, holder);
            holder.lblDetalhes.setTag(R.string.tag2, iPosition);
        }
        catch (Exception err)
        {
            Toast.makeText(context, "Erro onClick AdapterRepositorios: " + err.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount()
    {
        return arrItens.size();
    }

    @Override
    public void onClick(View view)
    {
        RespositoriosViewHolder holder = null;
        int iPosicao = -1;

        try
        {
            holder = (RespositoriosViewHolder) view.getTag(R.string.tag1);

            // Se a view clicada for a de detalhes
            if( view == holder.lblDetalhes )
            {
                // Obtem a posicao do item clicado
                iPosicao = (int) view.getTag(R.string.tag2);
                activityRepositorios.abreTelaDetalhes(arrItens.get(iPosicao));
            }
        }
        catch (Exception err)
        {
            Toast.makeText(context, "Erro onClick AdapterRepositorios: " + err.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static class RespositoriosViewHolder extends RecyclerView.ViewHolder
    {
        // Controles
        ImageView imgAvatar = null;
        TextView lblRepoName = null;
        TextView lblRepoLanguage = null;
        TextView lblDetalhes = null;

        public RespositoriosViewHolder(@NonNull View itemView)
        {
            super(itemView);

            // Obtendo as referencias do xml
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            lblRepoName = (TextView) itemView.findViewById(R.id.lblRepoName);
            lblRepoLanguage = (TextView) itemView.findViewById(R.id.lblRepoLanguage);
            lblDetalhes = (TextView) itemView.findViewById(R.id.lblDetalhes);
        }
    }
}
