package com.adithya.cinexperiencemovieapp.repository

import com.adithya.cinexperiencemovieapp.api.RetrofitClient
import com.adithya.cinexperiencemovieapp.model.Genre
import com.adithya.cinexperiencemovieapp.model.Movie
import com.adithya.cinexperiencemovieapp.model.Feedback

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

    suspend fun getFeedbacks(movieId: Int): List<Feedback> {
        val response = RetrofitClient.api.getFeedbacks(movieId, apiKey)
        return response.results.map {
            Feedback(
                id = it.id,
                username = it.author,
                comment = it.content
            )
        }
    }




}
