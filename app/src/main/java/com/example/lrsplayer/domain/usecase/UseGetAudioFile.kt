package com.example.lrsplayer.domain.usecase

import com.example.lrsplayer.domain.repository.LocalRepository
import java.io.File

class UseGetAudioFile(
    private val localRepository: LocalRepository
) {

    fun execute(name:String):File{
        return localRepository.getAudioFile(name)
    }

}