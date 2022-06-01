package com.example.moviemvi.resource

import com.example.moviemvi.models.room_data_base.entity.MoveNewPlayingEntity
import com.example.moviemvi.models.room_data_base.entity.MovePopularEntity


sealed class MoveResource {

    object Loading : MoveResource()
    data class Succes(val list: List<MovePopularEntity>, var list2: List<MoveNewPlayingEntity>) : MoveResource()

    data class Error(val message: String) : MoveResource()

}
