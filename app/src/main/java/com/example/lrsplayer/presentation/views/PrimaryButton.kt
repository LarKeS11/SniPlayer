package com.example.lrsplayer.presentation.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lrsplayer.presentation.theme.sf_pro_text

@Composable
fun PrimaryButton(
    text:String,
    onClick:() -> Unit
) {

    Button(
        onClick = {  },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0D6EFD))
    ) {

        Text(
            text = text,
            fontFamily = sf_pro_text,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color(0xFFFFFFFF)
        )

    }

}