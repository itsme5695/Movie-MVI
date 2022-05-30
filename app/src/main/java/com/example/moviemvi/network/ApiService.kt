package com.example.moviemvi.network

import com.example.moviemvi.models.Movie_Model
import com.example.moviemvi.models.Result
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("search/movie?api_key=a82817a0b228f49f3be9fdfeae2c5111&query={search_key}")
    suspend fun searchMovie(@Path("search_key") search_key: String): List<Result>

    @GET("movie/{movie_id}?api_key=a82817a0b228f49f3be9fdfeae2c5111")
    suspend fun getMovieById(@Path("movie_id") movie_id: String): Result

    @GET("movie/popular?api_key=a82817a0b228f49f3be9fdfeae2c5111")
    suspend fun getPopularMovies(): Movie_Model

    @GET("movie/{movie_id}/recommendations?api_key=a82817a0b228f49f3be9fdfeae2c5111")
    suspend fun getMovieSuggestions(@Path("movie_id") movie_id: String): Movie_Model

    @GET("movie/upcoming?api_key=a82817a0b228f49f3be9fdfeae2c5111")
    suspend fun getUpcomingMovies(): Movie_Model
}