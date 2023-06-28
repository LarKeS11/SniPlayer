package com.example.lrsplayer.presentation.screen.views

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.lrsplayer.until.ThemeColors

@Composable
fun DialogWrapper(
    modifier: Modifier = Modifier,
    colors:ThemeColors,
    onDismiss:() -> Unit,
    content:@Composable () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            backgroundColor = colors.main_background,
            modifier = modifier,
            shape = RoundedCornerShape(7.dp)
        ) {
            content()
        }
    }
}