package com.example.lrsplayer.data.local.image_service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class ImageService(
    private val context: Context
):ImageServiceInstance {


    override fun getBitmapByBytesArray(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

}