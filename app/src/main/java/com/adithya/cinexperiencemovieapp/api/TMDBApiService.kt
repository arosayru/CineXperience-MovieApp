package com.adithya.cinexperiencemovieapp.api

import com.adithya.cinexperiencemovieapp.model.MovieResponse
import com.adithya.cinexperiencemovieapp.model.GenreResponse
import com.adithya.cinexperiencemovieapp.model.FeedbackResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApiService {

    @GET("3/genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String
    ): GenreResponse

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): MovieResponse

    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String
    ): MovieResponse

    @GET("3/movie/{movie_id}/reviews")
    suspend fun getFeedbacks(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): FeedbackResponse

}
