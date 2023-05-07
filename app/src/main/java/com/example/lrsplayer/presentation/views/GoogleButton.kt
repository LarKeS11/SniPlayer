package com.example.lrsplayer.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lrsplayer.until.ThemeColors
import com.example.lrsplayer.R

@Composable
fun GoogleButton(
    colors: ThemeColors,
    callback:() -> Unit
) {

    Button(
        onClick = {
            callback()
         },
        modifier = Modifier.size(60.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = colors.second_main_background),
        shape = RoundedCornerShape(5.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.google_icon),
            contentDescription = "",
            modifier = Modifier.size(40.dp)
        )
    }

}