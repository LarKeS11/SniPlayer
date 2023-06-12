package com.example.lrsplayer.presentation.screen.music_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lrsplayer.presentation.theme.sf_pro_text
import com.example.lrsplayer.until.ThemeColors

@Composable
fun SearchBar(
    text:String,
    colors: ThemeColors,
    onChange:(String) -> Unit
) {

    BasicTextField(
        value = text,
        onValueChange = onChange,
        textStyle = TextStyle(
            color = colors.title.copy(alpha = 0.8f),
            fontSize = 14.sp,
            fontFamily = sf_pro_text,
            fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier
            .height(20.dp)
            .width(250.dp),
        decorationBox = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Divider(modifier = Modifier.fillMaxWidth().height(1.dp).background(colors.title))
            }
            it()
        }
    )
}