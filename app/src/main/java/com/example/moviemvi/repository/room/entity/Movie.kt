package com.example.moviemvi.repository.room.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
class Movie(
    @PrimaryKey @NonNull
    val imdbId: String,
    val title: String? = null,
    val plot: String? = null,
    val poster: String? = null,
    val imdbRating: String? = null,
    val language: String? = null,
    val ratings: String? = null,
    val runtime: String? = null,
    val sort_of_movie:String? = null
)

//val poster = movieModel.Poster
//val title = movieModel.Title
//val plot = movieModel.Plot
//val imdbRating = movieModel.imdbRating
//val language = movieModel.Language
//val ratings = movieModel.Ratings[0]
//val runtime = movieModel.Runtime