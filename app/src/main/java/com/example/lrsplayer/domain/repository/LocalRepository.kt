package com.example.lrsplayer.domain.repository

import android.net.Uri
import java.io.File

interface LocalRepository {

    fun getAudioFireFromUri(uri:Uri):File

}