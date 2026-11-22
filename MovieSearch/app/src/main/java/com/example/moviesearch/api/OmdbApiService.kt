package com.example.moviesearch.api

import retrofit2.http.GET
import retrofit2.http.Query

//This gets and sends requests with the apiKey

interface OmdbApiService {
    @GET("/")
    suspend fun searchMovies(
        @Query("s") searchQuery: String,
        @Query("apikey") apiKey: String
    ): OmdbResponse

    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") imdbID: String,
        @Query("apikey") apiKey: String
    ): OmdbMovieDetailResponse

}

