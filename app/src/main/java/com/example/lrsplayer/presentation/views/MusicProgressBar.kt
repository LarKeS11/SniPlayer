package com.example.lrsplayer.presentation.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.lrsplayer.until.ThemeColors

@Composable
fun MusicProgressBar(
    progress:Float,
    colors: ThemeColors
) {
    
    LinearProgressIndicator(
        progress = progress,
        color = colors.title,
        backgroundColor = colors.main_background,
        modifier = Modifier.height(4.dp).fillMaxWidth().clip(RoundedCornerShape(100))
    )
    
    
}