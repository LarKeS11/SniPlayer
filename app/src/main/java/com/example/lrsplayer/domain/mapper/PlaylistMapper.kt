package com.example.lrsplayer.domain.mapper

import com.example.lrsplayer.data.local.database_service.entity.PlaylistEntity
import com.example.lrsplayer.domain.model.Playlist
import java.io.File

fun Playlist.toPlaylistEntity():PlaylistEntity{
    val arr = musics.map { it.toString() }.joinToString(" ")
    return PlaylistEntity(
        id = id,
        name = name,
        imgSrc = imgSrc,
        musics = arr
    )
}

fun PlaylistEntity.toPlaylist(file: File? = null):Playlist{
    var arr = listOf<Int>()
    if(musics.isNotEmpty()){
        arr = musics.split(" ").map { it.toInt() }
    }
    return Playlist(
        id = id!!,
        name = name,
        imgSrc = imgSrc,
        musics = arr,
        imageFile = file
    )
}