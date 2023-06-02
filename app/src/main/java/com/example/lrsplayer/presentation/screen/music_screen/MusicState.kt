package com.example.lrsplayer.presentation.screen.music_screen

import com.example.lrsplayer.domain.model.Music

data class MusicState(
    val isLoading:Boolean = false,
    val data:List<Music> = listOf(),
    val error:String = "",
    val currentMusic:Music? = null,
    val musicPause:Boolean = false
)