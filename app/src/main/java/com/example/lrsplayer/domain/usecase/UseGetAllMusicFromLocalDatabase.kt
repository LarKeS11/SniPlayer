package com.example.lrsplayer.domain.usecase

import android.media.MediaMetadataRetriever
import android.util.Log
import com.example.lrsplayer.domain.mapper.toMusic
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.domain.repository.LocalRepository
import com.example.lrsplayer.until.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UseGetAllMusicFromLocalDatabase(
    private val localRepository: LocalRepository
) {



    operator fun invoke():Flow<Resource<List<Music>>> = flow {

        emit(Resource.Loading())
        try {
            val data = localRepository.getAllMusicFromDatabase().map {
                val metadata = localRepository.getMusicMetadata(it.path)
                Music(
                    id = it.id,
                    name = it.name,
                    path = it.path,
                    author = metadata.author
                )
            }
            emit(Resource.Success(data))
        }catch (e:Exception){
            emit(Resource.Error(e.toString()))
        }

    }

}
