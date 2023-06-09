package com.example.lrsplayer.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lrsplayer.until.ThemeColors

@Composable
fun SettingIcon(
    colors:ThemeColors,
    callback:() -> Unit
) {

    Button(
        contentPadding = PaddingValues(0.dp),
        onClick = { callback()},
        elevation = ButtonDefaults.elevation(0.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Divider(
                modifier = Modifier.size(4.dp).clip(RoundedCornerShape(100)).background(colors.title)
            )
            Divider(
                modifier = Modifier.size(4.dp).clip(RoundedCornerShape(100)).background(colors.title)
            )
            Divider(
                modifier = Modifier.size(4.dp).clip(RoundedCornerShape(100)).background(colors.title)
            )
        }
    }

}