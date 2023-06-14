package com.example.lrsplayer.presentation

import com.example.lrsplayer.domain.model.Music

data class MusicControlState(
    val musicPause:Boolean = false,
    val musics:List<Music> = listOf(),
    val isLooping:Boolean = false
)