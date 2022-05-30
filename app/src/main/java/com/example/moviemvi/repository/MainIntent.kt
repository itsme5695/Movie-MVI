package com.example.moviemvi.repository

sealed class MainIntent {
    object FetchUser : MainIntent()
}