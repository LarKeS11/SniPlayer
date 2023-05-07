package com.example.lrsplayer.presentation.views

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.lrsplayer.presentation.theme.sf_pro_text

@Composable
fun HighLightClickableText(
    text:String,
    callback: () -> Unit
){

    ClickableText(
        text = AnnotatedString(text),
        style = TextStyle(
            color = Color(0xFF0D6EFD),
            fontFamily = sf_pro_text,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    ){
        callback()
    }

}