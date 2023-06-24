package com.example.lrsplayer.presentation.screen.playlist_musics_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lrsplayer.presentation.theme.sf_pro_text
import com.example.lrsplayer.until.ThemeColors

@Composable
fun PlaylistMusicsScreen(
    playlistId:Int,
    colors:ThemeColors
) {
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){

        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {  }
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "",
                        modifier = Modifier
                            .width(30.dp)
                            .height(25.dp)
                        ,
                        tint = colors.title
                    )
                }
                Spacer(modifier = Modifier.width(62.dp))
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.background(Color.Green)
                ) {
                    Text(
                        text = "My playlist",
                        color = colors.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = sf_pro_text
                    )
                    IconButton(onClick = {  }) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "",
                            modifier = Modifier
                                .width(15.dp)
                                .height(15.dp),
                            tint = colors.title
                        )
                    }

                    
                }

            }
        }

    }
    
}