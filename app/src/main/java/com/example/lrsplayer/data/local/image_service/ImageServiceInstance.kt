package com.example.lrsplayer.data.local.image_service

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File

interface ImageServiceInstance {

    fun getBitmapByBytesArray(byteArray: ByteArray):Bitmap


}