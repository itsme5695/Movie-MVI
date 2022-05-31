package com.example.moviemvi.repository

import androidx.lifecycle.LiveData
import com.example.moviemvi.models.one_item.MovieModel
import com.example.moviemvi.network.ApiService
import com.example.moviemvi.repository.room.dao.AppDao
import com.example.moviemvi.repository.room.entity.Movie
import retrofit2.Response

class MovieRepository(
    private val apiService: ApiService,
    private val appDao: AppDao
) {

    suspend fun getMovie(movieId: String): Response<MovieModel> {
        return apiService.getMovie(movieId = movieId)
    }

    suspend fun insertMovie(movie: Movie) {
        appDao.insert(movie)
    }

    suspend fun getItem(id: String): Movie? {
        return appDao.getMovieById(id)
    }

    suspend fun deleteMovie(movie: Movie) {
        appDao.delete(movie)
    }

    fun getAllMovie(): LiveData<List<Movie>> {
        return appDao.movieList()
    }
}