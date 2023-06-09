package com.example.lrsplayer.presentation.screen.music_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lrsplayer.presentation.theme.sf_pro_text
import com.example.lrsplayer.until.ThemeColors

@Composable
fun SettingMenu(
    colors:ThemeColors,
    onRemove:()->Unit
) {

    Card(
        backgroundColor = colors.main_background,
        elevation = 3.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 15.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    Log.d("sdfgsdfsdf","######")
                    onRemove()
                          },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                elevation = ButtonDefaults.elevation(0.dp),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.defaultMinSize(minWidth = 100.dp)
            ) {
                Text(
                    text = "remove",
                    fontSize = 13.sp,
                    color = Color.Red,
                    fontFamily = sf_pro_text,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

}