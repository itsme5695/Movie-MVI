package com.example.moviemvi.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.moviemvi.repository.SourceRepository
import com.example.moviemvi.resource.SearchResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SourceViewModel(private val repository: SourceRepository) : ViewModel() {

    private val stateFlow = MutableStateFlow<SearchResource>(SearchResource.Loading)

    private val currentState = MutableLiveData(CURRENT_VALUE)

    init {
        fetchResource()
    }

    private fun fetchResource() {
        viewModelScope.launch {
            repository.getSearchedItem
                .catch {
                    stateFlow.emit(SearchResource.Error(it.message ?: ""))
                }
                .collect {
                    if (it.isSuccessful) {
                        stateFlow.emit(SearchResource.Success(it.body()))
                    } else {
                        when {
                            it.code() in 400..499 -> {
                                stateFlow.emit(SearchResource.Error("Error from client"))
                            }
                            it.code() in 500..599 -> {
                                stateFlow.emit(SearchResource.Error("Error from server"))
                            }
                            else -> {
                                stateFlow.emit(SearchResource.Error("Other Errors"))
                            }
                        }
                    }
                }
        }
    }

    fun getSearchedMovie(): StateFlow<SearchResource> {
        return stateFlow
    }

    fun getSearchedList(query: String) {
        currentState.value = query
    }

    val getMovie = currentState.switchMap {
        repository.getSearchResult(it).cachedIn(viewModelScope)
    }

    companion object {
        const val CURRENT_VALUE = "all"
    }

}