package com.example.moviemvi.respository

import com.example.moviemvi.models.retrofit.ApiServis
import com.example.moviemvi.models.room_data_base.app_data_base.AppDatabase
import com.example.moviemvi.models.room_data_base.entity.MoveNewPlayingEntity
import com.example.moviemvi.models.room_data_base.entity.MovePopularEntity

class MoveRepository(private val apiServis: ApiServis, private val appDatabase: AppDatabase) {
    suspend fun getAllMoveDataFromRemote() = apiServis.getData("da2e293fb61c37caee5c02647d22c537")
    suspend fun getNewMovePlaying() = apiServis.getMoveNowPlaying("da2e293fb61c37caee5c02647d22c537")

    suspend fun addMoveNewPlaying(list:List<MoveNewPlayingEntity>) = appDatabase.moveDao().addNewPlaying(list)
    suspend fun addMoveDataBase(list: List<MovePopularEntity>) = appDatabase.moveDao().addMovePopularList(list)

}