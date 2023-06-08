package com.example.lrsplayer.presentation.screen.music_screen

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lrsplayer.R
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.presentation.theme.sf_pro_text
import com.example.lrsplayer.presentation.views.MusicProgressBar
import com.example.lrsplayer.until.ThemeColors

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SmallMusicPanel(
    music: Music,
    musicImage:@Composable (Modifier) -> Unit,
    progress:Float,
    onProgressChange:(Float)->Unit,
    pause:Boolean,
    colors:ThemeColors,
    onNext:()->Unit,
    onLast:()->Unit,
    onPause:()->Unit,
    onClick:()->Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp),
        backgroundColor = colors.music_panel_color,
        onClick = {
            onClick()
        }
    ) {

        Row(modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
            .padding(start = 17.dp)
            .padding(end = 35.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row() {
                musicImage(
                    Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(6.dp)))
                Spacer(modifier = Modifier.width(23.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = if(music.name.length > 16)music.name.substring(0,15) + "..." else music.name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = sf_pro_text,
                        color = colors.title
                    )
                    Text(
                        text = music.author ?: "no author",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = sf_pro_text,
                        color = colors.title
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    IconMusicPanel(
                        colors = colors,
                        res = R.drawable.last_button
                    ){
                        onLast()
                    }
                    IconMusicPanel(
                        colors = colors,
                        res = if(pause) R.drawable.play else R.drawable.pause_button
                    ){
                        onPause()
                    }
                    IconMusicPanel(
                        colors = colors,
                        res = R.drawable.next_button
                    ){
                        onNext()
                    }
                }
                MusicProgressBar(
                    progress = progress,
                    colors = colors,
                    modifier = Modifier.width(82.dp).height(3.dp),
                    noRadius = true
                ){progress ->
                    onProgressChange(progress)
                }
            }
        }

    }

}

@Composable
fun IconMusicPanel(
    res:Int,
    colors: ThemeColors,
    onClick:()->Unit
){
    IconButton(
        onClick = {
            onClick()
        },
        modifier = Modifier.size(21.dp)
    ) {
        Icon(
            painter = painterResource(id = res),
            contentDescription = "",
            modifier = Modifier.size(21.dp),
            tint = colors.title
        )
    }
}