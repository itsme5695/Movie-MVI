package com.example.moviemvi.models.movie_image

data class Movie_Image(
    val backdrops: List<Backdrop>,
    val id: Int,
    val logos: List<Logo>,
    val posters: List<Poster>
)