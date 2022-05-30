package com.example.moviemvi.repository

import com.example.moviemvi.network.ApiService

class MainRepository(private val apiService: ApiService, private var str:String) {
    suspend fun searchMovie() = apiService.searchMovie(str)

    suspend fun getMovieById() = apiService.getMovieById(str)
    suspend fun getPopularMovies() = apiService.getPopularMovies()

    suspend fun getMovieSuggestions() = apiService.getMovieSuggestions(str)
    suspend fun getUpcomingMovies() = apiService.getUpcomingMovies()

}