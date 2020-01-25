package com.example.testewf_api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GitHubOwner implements Serializable
{
    // Variaveis da classe
    @SerializedName("login")
    public String sLogin = "";

    @SerializedName("avatar_url")
    public String sAvatarUrl = "";

    // Contrutor da classe
    public GitHubOwner(String sLoginParam, String sAvatarUrlParam)
    {
        this.sLogin = sLoginParam;
        this.sAvatarUrl = sAvatarUrlParam;
    }
}
