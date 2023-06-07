package com.example.lrsplayer.presentation.views

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.lrsplayer.until.ThemeColors
import com.smarttoolfactory.slider.ColorfulSlider
import com.smarttoolfactory.slider.MaterialSliderDefaults

@Composable
fun MusicProgressBar(
    modifier: Modifier = Modifier,
    progress:Float,
    noRadius:Boolean = false,
    colors: ThemeColors,
    setProgress:(Float)->Unit
) {


    var progress1 by remember { mutableStateOf(0f) }


    ColorfulSlider(
        value = progress,
        modifier = modifier,
        onValueChange = { value ->
            setProgress(value)
        },
        colors = MaterialSliderDefaults.materialColors(),
        thumbRadius = if(noRadius) 0.dp else 4.dp
    )
//
//    LinearProgressIndicator(
//        progress = progress,
//        color = colors.title,
//        backgroundColor = colors.main_background,
//        modifier = Modifier.height(4.dp).fillMaxWidth().clip(RoundedCornerShape(100))
//    )
//
    
}