package com.adithya.cinexperiencemovieapp.repository

import com.adithya.cinexperiencemovieapp.api.RetrofitClient
import com.adithya.cinexperiencemovieapp.model.Genre
import com.adithya.cinexperiencemovieapp.model.Movie

class MovieRepository {

    private val apiKey = "f9923821f549f034afb399cd27e37afd"

    suspend fun getGenres(): List<Genre> {
        return RetrofitClient.api.getGenres(apiKey).genres
    }

    suspend fun getPopularMovies(): List<Movie> {
        return RetrofitClient.api.getPopularMovies(apiKey).results
    }

    suspend fun getUpcomingMovies(): List<Movie> {
        return RetrofitClient.api.getUpcomingMovies(apiKey).results
    }
}
