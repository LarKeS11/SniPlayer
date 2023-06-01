package com.example.lrsplayer.presentation.screen.music_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.lrsplayer.R
import com.example.lrsplayer.presentation.theme.sf_pro_text
import com.example.lrsplayer.presentation.views.MusicProgressBar
import com.example.lrsplayer.presentation.views.SettingIcon
import com.example.lrsplayer.until.ThemeColors

@Composable
fun MusicControl(
    colors: ThemeColors,
    callback:() -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colors.main_background)
    ){
        Image(
            painter = painterResource(id = R.drawable.test_back),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier.fillMaxSize().background(colors.main_background.copy(alpha = 0.95f))
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(top = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            callback()
                        },
                        modifier = Modifier.size(29.dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                            contentDescription = "",
                            modifier = Modifier.size(29.dp)
                        )
                    }
                    SettingIcon(colors = colors) {

                    }
                }
            }

            item { 
                Spacer(modifier = Modifier.height(45.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 45.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.test_back),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(302.dp)
                            .clip(RoundedCornerShape(26.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = "Nakhla",
                    fontFamily = sf_pro_text,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 23.sp,
                    color = colors.title
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Hidden & Khalse & Sijal",
                    fontFamily = sf_pro_text,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp,
                    color = colors.title
                )
            }
            item {
                Spacer(modifier = Modifier.height(30.dp))
                Column(
                    modifier = Modifier
                        .padding(horizontal = 42.dp)
                        .align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(52.dp)
                    ) {
                        IconButton(
                            onClick = {  },
                            modifier = Modifier.size(20.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.__icon__shuffle_),
                                contentDescription = "",
                                modifier = Modifier.size(20.dp),
                                tint = colors.title
                            )
                        }
                        IconButton(
                            onClick = {  },
                            modifier = Modifier.size(20.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_repeat_24),
                                contentDescription = "",
                                modifier = Modifier.size(20.dp),
                                tint = colors.title
                            )
                        }
                        IconButton(
                            onClick = {  },
                            modifier = Modifier.size(20.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.unfilledheart),
                                contentDescription = "",
                                modifier = Modifier.size(20.dp),
                                tint = colors.title
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    MusicProgressBar(
                        progress = 0.5f,
                        colors = colors
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "1:53",
                            fontFamily = sf_pro_text,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp,
                            color = colors.title
                        )
                        Text(
                            text = "4:42",
                            fontFamily = sf_pro_text,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp,
                            color = colors.title
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        IconButton(
                            onClick = {  },
                            modifier = Modifier.size(34.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.last_button),
                                contentDescription = "",
                                modifier = Modifier.size(34.dp),
                                tint = colors.title
                            )
                        }
                        IconButton(
                            onClick = {  },
                            modifier = Modifier.size(34.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.pause_button),
                                contentDescription = "",
                                modifier = Modifier.size(34.dp),
                                tint = colors.title
                            )
                        }
                        IconButton(
                            onClick = {  },
                            modifier = Modifier.size(34.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.next_button),
                                contentDescription = "",
                                modifier = Modifier.size(34.dp),
                                tint = colors.title
                            )
                        }
                    }
                }
            }

        }
    }



}