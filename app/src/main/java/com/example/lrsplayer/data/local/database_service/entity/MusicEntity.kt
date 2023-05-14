package com.example.lrsplayer.data.local.database_service.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MusicEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null,
    val name:String,
    val path:String
)