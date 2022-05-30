package com.example.moviemvi.repository

import com.example.moviemvi.models.Movie_Model
import com.example.moviemvi.models.Result

sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    data class MoviesById(val movie: Result) : MainState()
    data class MoviesSearch(val movie: List<Result>) : MainState()
    data class MoviesPopular(val movie: Movie_Model) : MainState()
    data class MoviesSuggestions(val movie: Movie_Model) : MainState()
    data class MoviesUpcoming(val movie: Movie_Model) : MainState()
    data class Error(val error: String?) : MainState()
}