package com.example.moviemvi.network

import com.example.moviemvi.models.SearchedModel
import com.example.moviemvi.models.one_item.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/")
    suspend fun getMovies(
        @Query("apikey") apikey: String = MY_API_KEY,
        @Query("type") type: String = "movie",
        @Query("plot") plot: String = "full",
        @Query("r") r: String = "json",
        @Query("v") version: Int = 1,
        @Query("s") search: String = "all",
        @Query("page") page: Int = 1
    ): Response<SearchedModel>

    @GET("/")
    suspend fun getMovie(
        @Query("apikey") apikey: String = MY_API_KEY,
        @Query("plot") plot: String = "full",
        @Query("r") r: String = "json",
        @Query("v") version: String = "1",
        @Query("i") movieId: String
    ): Response<MovieModel>
}