package com.example.moviemvi.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviemvi.network.ApiService
import com.example.moviemvi.repository.MainRepository

class ViewModelFactory(private val apiService: ApiService, private val str: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiService, str)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}