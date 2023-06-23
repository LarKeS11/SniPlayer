package com.example.lrsplayer.presentation.screen.playlist_screen

import com.example.lrsplayer.domain.model.Playlist

data class PlayListScreenState(

    val playlists:List<List<Playlist>> = listOf(),
    val isLoading:Boolean = false,
    val error:String = ""

)