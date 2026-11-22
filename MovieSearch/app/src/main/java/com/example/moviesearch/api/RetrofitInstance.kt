package com.example.moviesearch.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// This connects things to the internet and sets things up for the API key
object RetrofitInstance {

    private const val BASE_URL = "https://www.omdbapi.com/"
    private const val API_KEY = "d0a3c46a"

    val api: OmdbApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OmdbApiService::class.java)
    }

    fun getApiKey(): String {
        return API_KEY
    }
}