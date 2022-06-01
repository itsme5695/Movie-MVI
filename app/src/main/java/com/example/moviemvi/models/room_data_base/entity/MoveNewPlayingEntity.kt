package com.example.moviemvi.models.room_data_base.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class MoveNewPlayingEntity(
    @PrimaryKey()
    var id: Int,
    var title: String,
    var description: String,
    var image_url: String,
    var release_date: String,
    var rank: String,
    var favrorite: Boolean
) : Serializable {

}