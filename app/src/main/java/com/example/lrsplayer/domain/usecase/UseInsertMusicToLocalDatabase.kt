package com.example.lrsplayer.domain.usecase

import com.example.lrsplayer.domain.mapper.toMusicEntity
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.domain.repository.LocalRepository

class UseInsertMusicToLocalDatabase(
    private val localRepository: LocalRepository
) {

    suspend fun execute(music: Music){
        localRepository.insertMusicToDatabase(music.toMusicEntity())
    }

}