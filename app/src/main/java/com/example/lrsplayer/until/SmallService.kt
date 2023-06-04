package com.example.lrsplayer.until

import java.util.concurrent.TimeUnit

object SmallService {

    fun convertMillisecondsToTimeString(total:Int):String{
       return String.format(
            "%02d:%02d ",
            TimeUnit.MILLISECONDS.toMinutes(total.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(total.toLong()) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(total.toLong()))
        )
    }

}