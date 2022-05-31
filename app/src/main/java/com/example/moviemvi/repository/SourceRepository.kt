package com.example.moviemvi.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.moviemvi.network.ApiService
import com.example.moviemvi.pager.PagerSource
import kotlinx.coroutines.flow.flow

class SourceRepository(private val apiService: ApiService) {

    val getSearchedItem = flow { emit(apiService.getMovies()) }

    fun getSearchResult(query: String) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            PagerSource(query, apiService)
        }
    ).liveData
}