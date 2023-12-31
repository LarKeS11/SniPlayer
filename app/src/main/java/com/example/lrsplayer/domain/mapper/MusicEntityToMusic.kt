package com.example.lrsplayer.domain.mapper

import com.example.lrsplayer.data.local.database_service.entity.MusicEntity
import com.example.lrsplayer.domain.model.Music

fun MusicEntity.toMusic():Music{
    return Music(
        id = id,
        name = name,
        path = path
    )
}