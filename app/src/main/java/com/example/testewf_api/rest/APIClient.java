package com.example.testewf_api.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient
{
    // Variaveis da classe
    public static final String BASE_URL = "https://api.github.com/search/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient()
    {
        // Se a variavel estiver nula
        if( retrofit == null )
        {
            // Cria uma nova instancia do retrofit
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}
