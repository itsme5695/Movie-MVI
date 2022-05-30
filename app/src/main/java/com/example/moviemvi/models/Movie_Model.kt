package com.example.moviemvi.models

data class Movie_Model(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)