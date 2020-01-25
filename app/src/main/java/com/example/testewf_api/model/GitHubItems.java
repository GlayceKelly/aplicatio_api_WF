package com.example.testewf_api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GitHubItems implements Serializable
{
    // Variaveis da classe
    @SerializedName("name")
    public String sName = "";

    @SerializedName("description")
    public String sDescription = "";

    @SerializedName("language")
    public String sLanguage = "";

    @SerializedName("owner")
    public GitHubOwner gitHubOwner = null;

    // Construtor da classe
    public GitHubItems(String sName, String sDescription, String sLanguage, GitHubOwner gitHubOwnerParam)
    {
        this.sName = sName;
        this.sDescription = sDescription;
        this.sLanguage = sLanguage;
        this.gitHubOwner = gitHubOwnerParam;
    }
}
