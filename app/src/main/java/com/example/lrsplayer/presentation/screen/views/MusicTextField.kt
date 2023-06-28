package com.example.lrsplayer.presentation.screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lrsplayer.presentation.theme.sf_pro_text
import com.example.lrsplayer.until.ThemeColors

@Composable
fun MusicTextField(
    text:String,
    colors: ThemeColors,
    modifier: Modifier = Modifier,
    onChange:(String) -> Unit
) {



    BasicTextField(
        value = text,
        onValueChange = {
            onChange(it)
        },
        textStyle = TextStyle(
            color = colors.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = sf_pro_text
        ),
        modifier = modifier,
        decorationBox = {
            it()
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(colors.title)
                )
            }

        }
    )

}