package com.example.moviemvi.repository

import com.example.moviemvi.models.Movie_Model
import com.example.moviemvi.models.Result
import com.example.moviemvi.models.movie_image.Movie_Image

sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    data class MoviesById(val movie: Result) : MainState()
    data class MoviesSearch(val search_movie: List<Result>) : MainState()
    data class MoviesPopular(val popular_ovie: Movie_Model) : MainState()
    data class MoviesSuggestions(val suggest_movie: Movie_Model) : MainState()
    data class MoviesUpcoming(val upcoming_movie: Movie_Model) : MainState()
    data class MovieImages(val image_movie: Movie_Image) : MainState()
    data class Error(val error: String?) : MainState()
}