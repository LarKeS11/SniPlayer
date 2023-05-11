package com.example.lrsplayer.presentation.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.lrsplayer.presentation.theme.sf_pro_text

@Composable
fun ErrorView(
    error:String,
    size:Int
) {

    Text(
        text = error,
        color = Color(0xE6CA4444),
        fontSize = size.sp,
        fontFamily = sf_pro_text,
        fontWeight = FontWeight.SemiBold
    )

}