package com.adithya.cinexperiencemovieapp.api

import com.adithya.cinexperiencemovieapp.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.adithya.cinexperiencemovieapp.model.GenreResponse

interface TMDBApiService {
    @GET("3/genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String
    ): GenreResponse

    @GET("3/movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): MovieResponse

    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String): MovieResponse

}