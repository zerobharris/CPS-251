package com.example.moviesearch

import com.example.moviesearch.api.RetrofitInstance
import com.example.moviesearch.api.OmdbResponse
import com.example.moviesearch.api.OmdbMovieDetailResponse
class MovieRepository {
    private val api = RetrofitInstance.api
    private val apiKey = RetrofitInstance.getApiKey()
    suspend fun searchMovies(query: String): Result<OmdbResponse> {
        return runCatching {
            api.searchMovies(query, apiKey)
        }
    }

    suspend fun getMovieDetails(imdbID: String): Result<OmdbMovieDetailResponse> {
        return runCatching {
            api.getMovieDetails(imdbID, apiKey)
        }
    }
}