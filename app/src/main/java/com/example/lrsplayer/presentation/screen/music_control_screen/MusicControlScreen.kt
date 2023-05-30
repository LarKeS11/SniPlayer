package com.example.lrsplayer.presentation.screen.music_control_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lrsplayer.until.ThemeColors
import com.example.lrsplayer.R
import com.example.lrsplayer.presentation.views.SettingIcon

@Composable
fun MusicControlScreen(
    id:Int,
    colors: ThemeColors
) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(colors.main_background)){
        LazyColumn(modifier = Modifier.fillMaxSize()){
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(26.dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                            contentDescription = "",
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    SettingIcon(colors = colors) {
                        
                    }
                }
            }
        }
    }

}