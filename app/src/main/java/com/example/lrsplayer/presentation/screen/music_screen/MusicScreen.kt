package com.example.lrsplayer.presentation.screen.music_screen

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lrsplayer.R
import com.example.lrsplayer.presentation.theme.sf_pro_text
import com.example.lrsplayer.presentation.views.MusicButton
import com.example.lrsplayer.until.ThemeColors

@Composable
fun MusicScreen(
    colors:ThemeColors,
    viewModel: MusicViewModel = hiltViewModel(),
    navController: NavController
) {

    val state by viewModel.state.collectAsState()
    

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){res ->
        viewModel.saveMusic(res!!)
    }

    

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.main_background)
            .padding(top = 24.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 13.dp, end = 32.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {  },
                            modifier = Modifier
                                .width(16.dp)
                                .height(13.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.menu),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(16.dp)
                                    .height(13.dp),
                                tint = colors.title
                            )
                        }
                        Text(
                            text = "SniPlayer",
                            fontSize = 20.sp,
                            fontFamily = sf_pro_text,
                            fontWeight = FontWeight.ExtraBold,
                            color = colors.title
                        )
                    }
                    IconButton(
                        onClick = {  },
                        modifier = Modifier
                            .size(30.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_search_24),
                            contentDescription = "",
                            modifier = Modifier
                                .size(30.dp),
                            tint = colors.title
                        )
                    }

                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 17.dp)
                    .padding(top = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(25.dp)
                ) {
                    Text(
                        text = "Songs",
                        fontSize = 13.sp,
                        color = colors.title,
                        fontFamily = sf_pro_text,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Playlist",
                        fontSize = 13.sp,
                        color = colors.title,
                        fontFamily = sf_pro_text,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Albums",
                        fontSize = 13.sp,
                        color = colors.title,
                        fontFamily = sf_pro_text,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MusicButton(
                        icon = R.drawable.__icon__shuffle_,
                        colors = colors,
                        modifier = Modifier
                            .width(22.dp)
                            .height(20.dp)
                    ){

                    }
                    MusicButton(
                        icon = R.drawable.musicfilter,
                        colors = colors,
                        modifier = Modifier.size(22.dp)
                    ){

                    }
                    MusicButton(
                        icon = R.drawable.baseline_add_circle_outline_24,
                        colors = colors,
                        modifier = Modifier.size(23.dp)
                    ){
                        launcher.launch("audio/*")
                    }
                }

            }
        }
        
        
        items(state.data){
            Text(text = it.name)
        }
        

    }

    
}


