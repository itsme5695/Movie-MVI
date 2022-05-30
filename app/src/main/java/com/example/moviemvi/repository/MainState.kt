package com.example.moviemvi.repository

import com.example.moviemvi.models.Result

sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    data class Movies(val movie: Result) : MainState()
    data class Error(val error: String?) : MainState()
}