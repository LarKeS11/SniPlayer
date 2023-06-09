package com.example.lrsplayer.domain.usecase

import com.example.lrsplayer.domain.mapper.toMusicEntity
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.domain.repository.LocalRepository

class UseDeleteMusic(
    private val localRepository: LocalRepository
) {

    suspend fun deleteMusic(music:Music){
        localRepository.deleteMusic(music.toMusicEntity())
    }

}