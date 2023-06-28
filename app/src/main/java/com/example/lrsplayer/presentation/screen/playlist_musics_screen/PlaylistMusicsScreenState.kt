package com.example.lrsplayer.presentation.screen.playlist_musics_screen

import com.example.lrsplayer.domain.model.Music

data class PlaylistMusicsScreenState(
    val isLoading:Boolean = false,
    val data:List<Music> = listOf(),
    val error:String = ""
)