package com.example.lrsplayer.presentation.views

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lrsplayer.R
import com.example.lrsplayer.until.ThemeColors

@Composable
fun MusicButton(
    icon:Int,
    colors: ThemeColors,
    modifier:Modifier = Modifier,
    callback:() -> Unit
) {

    IconButton(
        onClick = {
        callback()
    },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            modifier = modifier,
            tint = colors.title
        )
    }

}