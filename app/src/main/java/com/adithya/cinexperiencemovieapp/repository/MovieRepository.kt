package com.adithya.cinexperiencemovieapp.repository

import com.adithya.cinexperiencemovieapp.api.RetrofitClient
import com.adithya.cinexperiencemovieapp.model.Genre
import com.adithya.cinexperiencemovieapp.model.Movie
import com.adithya.cinexperiencemovieapp.model.FeedbackItem

class MovieRepository {

    private val apiKey = "28b68a20e5e4059386f514b4a898cd31"

    suspend fun getGenres(): List<Genre> {
        return RetrofitClient.api.getGenres(apiKey).genres
    }

    suspend fun getPopularMovies(): List<Movie> {
        return RetrofitClient.api.getPopularMovies(apiKey).results
    }

    suspend fun getUpcomingMovies(): List<Movie> {
        return RetrofitClient.api.getUpcomingMovies(apiKey).results
    }

    suspend fun getFeedbacks(movieId: Int): List<FeedbackItem> {
        val response = RetrofitClient.api.getFeedbacks(movieId, apiKey)
        return response.results
    }
}
