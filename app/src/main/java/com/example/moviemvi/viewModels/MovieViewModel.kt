package com.example.moviemvi.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviemvi.repository.MovieRepository
import com.example.moviemvi.repository.room.entity.Movie
import com.example.moviemvi.resource.MovieResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val stateFlow = MutableStateFlow<MovieResource>(MovieResource.Loading)

    fun setMovieId(id: String): StateFlow<MovieResource> {
        viewModelScope.launch {
            val response = repository.getMovie(movieId = id)
            if (response.isSuccessful) {
                stateFlow.emit(MovieResource.Success(response.body()!!))
            } else {
                when {
                    response.code() in 400..499 -> {
                        stateFlow.emit(MovieResource.Error("Error from client"))
                    }
                    response.code() in 500..599 -> {
                        stateFlow.emit(MovieResource.Error("Error from server"))
                    }
                    else -> {
                        stateFlow.emit(MovieResource.Error("Other Errors"))
                    }
                }
            }
        }
        return stateFlow
    }

    suspend fun insertToDb(movie: Movie) {
        repository.insertMovie(movie)
    }

    suspend fun getMovie(id: String): Movie? {
        return repository.getItem(id)
    }

    fun deleteFomDb(movie: Movie) {
        viewModelScope.launch {
            repository.deleteMovie(movie)
        }
    }

    fun getAllMoviesFromDb(): LiveData<List<Movie>> {
        return repository.getAllMovie()
    }

}