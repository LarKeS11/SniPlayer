package com.example.lrsplayer.presentation.screen.music_screen

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.lrsplayer.domain.usecase.UseGetAudioFileFromUri
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val useGetAudioFileFromUri: UseGetAudioFileFromUri
):ViewModel() {

    fun getAudioFile(uri:Uri):File{
        return useGetAudioFileFromUri.execute(uri)
    }

}