package com.example.moviesearch.api

import com.google.gson.annotations.SerializedName

//This part actually gets the response searching
data class OmdbResponse(
    @SerializedName("Search") val search: List<MovieSearchItem>?,
    @SerializedName("totalResults") val totalResults: String?,
    @SerializedName("Response") val response: String?,
    @SerializedName("Error") val error: String?
)

data class MovieSearchItem(
    @SerializedName("Title") val title: String?,
    @SerializedName("Year") val year: String?,
    @SerializedName("imdbID") val imdbID: String?,
    @SerializedName("Type") val type: String?,
    @SerializedName("Poster") val poster: String?
)

data class OmdbMovieDetailResponse(
    @SerializedName("Title") val title: String?,
    @SerializedName("Year") val year: String?,
    @SerializedName("Rated") val rated: String?,
    @SerializedName("Director") val director: String?,
    @SerializedName("Actors") val actors: String?,
    @SerializedName("Poster") val poster: String?,
    @SerializedName("Ratings") val ratings: List<MovieRating>?,
    @SerializedName("imdbRating") val imdbRating: String?,
    @SerializedName("BoxOffice") val boxOffice: String?,
    @SerializedName("Plot") val plot: String?,
    @SerializedName("Response") val response: String?,
    @SerializedName("Error") val error: String?
)

data class MovieRating(
    @SerializedName("Source") val source: String?,
    @SerializedName("Value") val value: String?
)
