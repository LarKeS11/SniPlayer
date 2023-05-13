package com.example.lrsplayer.data.local.audio_service

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.FileUtils
import android.provider.MediaStore
import android.util.Log
import com.google.android.gms.common.util.IOUtils
import java.io.File
import java.io.FileOutputStream

class AudioService(
    private val context: Context,
):AudioServiceInterface {
    @SuppressLint("Range")
    override fun getAudioFireFromUri(audioUri: Uri): File {
        return File("sf")
    }
}