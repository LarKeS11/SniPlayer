package com.example.lrsplayer.presentation.screen.playlist_musics_screen

import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.domain.model.Playlist

data class PlaylistMusicsScreenState(
    val isLoading:Boolean = false,
    val playlist:Playlist? = null,
    val error:String = ""
)