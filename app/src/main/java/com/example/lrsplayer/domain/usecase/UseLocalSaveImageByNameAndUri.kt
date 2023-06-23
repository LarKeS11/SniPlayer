package com.example.lrsplayer.domain.usecase

import android.net.Uri
import com.example.lrsplayer.domain.repository.LocalRepository

class UseLocalSaveImageByNameAndUri(
    private val localRepository: LocalRepository
) {

    fun execute(name:String, uri:Uri){
        localRepository.saveImage(name = name, uri = uri)
    }

}