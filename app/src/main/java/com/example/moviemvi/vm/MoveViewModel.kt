package com.example.moviemvi.vm

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviemvi.models.retrofit.ApiServis
import com.example.moviemvi.models.room_data_base.app_data_base.AppDatabase
import com.example.moviemvi.models.room_data_base.entity.MoveNewPlayingEntity
import com.example.moviemvi.models.room_data_base.entity.MovePopularEntity
import com.example.moviemvi.resource.MoveResource
import com.example.moviemvi.respository.MoveRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import com.example.moviemvi.utils.NetworkHelper

class MoveViewModel(
    apiServis: ApiServis,
    private val networkHelper: NetworkHelper,
    private val appDatabase: AppDatabase
) : ViewModel() {


    private val moveRepository = MoveRepository(apiServis, appDatabase)

    val flow = MutableStateFlow<MoveResource>(MoveResource.Loading)
    fun getMove(): StateFlow<MoveResource> {

        viewModelScope.launch {
            try {
                if (networkHelper.isNetworkConnected()) {
                    val response = async { moveRepository.getAllMoveDataFromRemote() }
                    val response2 = async { moveRepository.getNewMovePlaying() }

                    if (response.await().isSuccessful && response2.await().isSuccessful) {

                        loadDataBase(
                            response.await().body()?.results as ArrayList<com.example.moviemvi.models.retrofit.model.Result>,
                            response2.await()
                                .body()?.results as ArrayList<com.example.moviemvi.models.retrofit.model_now_playing.Result>
                        )

                    } else {
                        flow.emit(MoveResource.Error(response.await().errorBody().toString()))
                    }
                } else {
                    val moveNewPlaying = appDatabase.moveDao().getMoveNewPlaying()
                    if (moveNewPlaying.isEmpty()) {
                        flow.emit(MoveResource.Loading)
                    } else {
                        flow.emit(
                            MoveResource.Succes(
                                appDatabase.moveDao().getMoveData(),
                                appDatabase.moveDao().getMoveNewPlaying()
                            )
                        )
                    }
                }

            } catch (e: Exception) {
                flow.emit(MoveResource.Error(e.message.toString()))
            }
        }
        return flow
    }

    private fun loadDataBase(
        listTopMove: ArrayList<com.example.moviemvi.models.retrofit.model.Result>,
        listNewMove: ArrayList<com.example.moviemvi.models.retrofit.model_now_playing.Result>,

        ) {
        viewModelScope.launch(Dispatchers.Default) {
            listTopMove.forEach {
                val movePopularData = appDatabase.moveDao().getMovePopularId(it.id)

                if (movePopularData == null) {
                    appDatabase.moveDao().addMoveDataPopular(
                        MovePopularEntity(
                            it.id, it.title, "https://image.tmdb.org/t/p/w500/" + it.poster_path,
                            it.overview,
                            it.release_date,
                            it.vote_average.toString(),
                            false
                        )
                    )

                }
            }
            listNewMove.forEach {
                val moveNewPlaying = appDatabase.moveDao().getMoveNewPlayingId(it.id)
                if (moveNewPlaying == null) {
                    appDatabase.moveDao().addNewMove(
                        MoveNewPlayingEntity(
                            it.id,
                            it.title,
                            it.overview,
                            "https://image.tmdb.org/t/p/w500/" + it.poster_path,
                            it.release_date,
                            it.vote_average.toString(), false
                        )
                    )
                }
            }

            flow.emit(
                MoveResource.Succes(
                    appDatabase.moveDao().getMoveData(),
                    appDatabase.moveDao().getMoveNewPlaying()
                )
            )
        }

    }


}