package com.example.lrsplayer.presentation.views

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lrsplayer.until.ThemeColors



@Composable
fun PlayingView(
    colors:ThemeColors
) {


    val transition = rememberInfiniteTransition()
    val translateAnim1 by transition.animateFloat(
        initialValue = 5f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
    )
    val translateAnim2 by transition.animateFloat(
        initialValue = 5f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
    )
    val translateAnim3 by transition.animateFloat(
        initialValue = 5f,
        targetValue = 14f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier.width(2.dp).height(1.dp * translateAnim1).background(colors.title)
        )
        Divider(
            modifier = Modifier.width(2.dp).height(1.dp * translateAnim2).background(colors.title)
        )
        Divider(
            modifier = Modifier.width(2.dp).height(1.dp * translateAnim3).background(colors.title)
        )
        Divider(
            modifier = Modifier.width(2.dp).height(1.dp * translateAnim2).background(colors.title)
        )
        Divider(
            modifier = Modifier.width(2.dp).height(1.dp * translateAnim1).background(colors.title)
        )

    }

}