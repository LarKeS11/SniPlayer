package com.example.lrsplayer.presentation.screen.views

import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lrsplayer.R
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.presentation.theme.sf_pro_text
import com.example.lrsplayer.presentation.views.PlayingView
import com.example.lrsplayer.until.ThemeColors

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MusicItem(
    modifier: Modifier = Modifier,
    music:Music,
    colors:ThemeColors,
    image:Bitmap? = null,
    isSelected:Boolean = false,
    onLongClick:() -> Unit = {},
    onClick:() -> Unit
) {
    Card(
        modifier = modifier
            .combinedClickable(
                onLongClick = {
                    onLongClick()
                },
                onClick = {
                    onClick()
                }
            )
        ,
        backgroundColor = if(isSelected) colors.second_main_background else Color.Transparent,
        elevation = 0.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (image == null) {
                    Image(
                        painterResource(id = R.drawable.no_music_image),
                        contentDescription = "",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(6.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        image!!.asImageBitmap(),
                        contentDescription = "",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(6.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = music.name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = sf_pro_text,
                        color = colors.title,
                        modifier = Modifier.width(200.dp)
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
        }

    }
}