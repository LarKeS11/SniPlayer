package com.example.lrsplayer.domain.mapper

import com.example.lrsplayer.data.local.database_service.entity.MusicEntity
import com.example.lrsplayer.domain.model.Music

fun Music.toMusicEntity():MusicEntity{
    return MusicEntity(
        id = id,
        name = name,
        path = path,
        image_src = image_src,
        author = author
    )
}