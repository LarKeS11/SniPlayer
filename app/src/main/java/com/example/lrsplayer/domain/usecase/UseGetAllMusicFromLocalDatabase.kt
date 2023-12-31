package com.example.lrsplayer.domain.usecase

import android.media.MediaMetadataRetriever
import android.util.Log
import androidx.compose.ui.text.toLowerCase
import com.example.lrsplayer.domain.mapper.toMusic
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.domain.repository.LocalRepository
import com.example.lrsplayer.until.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class UseGetAllMusicFromLocalDatabase(
    private val localRepository: LocalRepository
) {



    operator fun invoke(text:String? = null):Flow<Resource<List<Music>>> = flow {

        emit(Resource.Loading())
        try {
            var data = localRepository.getAllMusicFromDatabase().map {
                val metadata = localRepository.getMusicMetadata(it.path)
                Music(
                    id = it.id,
                    name = it.name,
                    path = it.path,
                    author = metadata.author ?: "no author"
                )
            }
            if(text != null){
                data = data.filter { it.name.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT)) ||  it.author!!.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT)) }
            }


            emit(Resource.Success(data))
        }catch (e:Exception){
            Log.d("sdfsdfsdfsdf",e.toString())
            emit(Resource.Error(e.message.toString()))
        }

    }

}
