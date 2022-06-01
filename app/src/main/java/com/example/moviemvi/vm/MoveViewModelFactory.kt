package com.example.moviemvi.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviemvi.models.retrofit.ApiServis
import com.example.moviemvi.models.room_data_base.app_data_base.AppDatabase
import com.example.moviemvi.utils.NetworkHelper
import java.lang.RuntimeException

class MoveViewModelFactory(
    private val networkHelper: NetworkHelper,
    private val apiServis: ApiServis,
    private val appDatabase: AppDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoveViewModel::class.java)) {
            return MoveViewModel(apiServis, networkHelper, appDatabase) as T
        }
        throw  RuntimeException("Error")
    }


}