package com.example.moviemvi.resource

import com.example.moviemvi.models.SearchedModel


sealed class SearchResource {

    object Loading : SearchResource()
    data class Success(val searchModel: SearchedModel?) : SearchResource()
    data class Error(val message: String) : SearchResource()
}