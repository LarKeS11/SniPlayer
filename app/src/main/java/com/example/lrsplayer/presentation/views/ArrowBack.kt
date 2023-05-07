package com.example.lrsplayer.presentation.views

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lrsplayer.R
import com.example.lrsplayer.until.ThemeColors

@Composable
fun ArrowBack(
    colors: ThemeColors,
    callback: () -> Unit
) {

    Button(
        onClick = {  },
        modifier = Modifier.size(44.dp),
        shape = RoundedCornerShape(100),
        colors = ButtonDefaults.buttonColors(backgroundColor = colors.second_main_background)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.left_back_arrow),
            contentDescription = "",
            modifier = Modifier.width(5.5.dp).height(11.5.dp),
            tint = colors.title
        )
    }

}