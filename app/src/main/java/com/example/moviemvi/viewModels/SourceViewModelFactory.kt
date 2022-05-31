package com.example.moviemvi.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviemvi.repository.SourceRepository

class SourceViewModelFactory(private val repository: SourceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SourceViewModel::class.java)) {
            return SourceViewModel(repository) as T
        }
        throw IllegalAccessException("view model error")
    }
}