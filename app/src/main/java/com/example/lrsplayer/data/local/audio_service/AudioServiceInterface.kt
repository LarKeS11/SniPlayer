package com.example.lrsplayer.data.local.audio_service

import android.net.Uri
import java.io.File

interface AudioServiceInterface {
    fun saveAudioFile(uri: Uri): String
    fun getAudioFile(name:String):File
}