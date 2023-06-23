package com.example.lrsplayer.data.local.database_service.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null,
    val imgSrc:String?,
    val name:String,
    val musics:String
)