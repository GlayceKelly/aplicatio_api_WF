package com.example.testewf_api.rest;

import com.example.testewf_api.model.GitHubRepositorios;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubRepoEndPoint
{
    @GET("repositories?q=language:swift&sort=stars/")
    Call<GitHubRepositorios> getRepo();
}
