package com.example.moviemvi.models

data class SearchedModel(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)