package com.example.moviemvi.resource

import com.example.moviemvi.models.one_item.MovieModel


sealed class MovieResource {

    object Loading : MovieResource()
    data class Error(val message: String) : MovieResource()
    data class Success(val movieModel: MovieModel): MovieResource()
}