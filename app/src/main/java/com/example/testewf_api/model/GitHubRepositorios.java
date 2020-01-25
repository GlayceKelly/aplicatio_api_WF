package com.example.testewf_api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GitHubRepositorios
{
    // Variaveis da classe
    @SerializedName("items")
    public ArrayList<GitHubItems> arrItens = null;

    // Construtor da classe
    public GitHubRepositorios(ArrayList<GitHubItems> arrItensParam)
    {
        arrItens = arrItensParam;
    }
}
