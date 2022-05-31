package com.example.moviemvi.repository.room.dao

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviemvi.repository.room.entity.Movie

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Movie)

    @Delete
    suspend fun delete(entity: Movie)

    @NonNull
    @Query("select * from movie_table where imdbid =:imdbid")
    suspend fun getMovieById(imdbid: String): Movie?

    @Query("SELECT * FROM movie_table")
    fun movieList(): LiveData<List<Movie>>
}