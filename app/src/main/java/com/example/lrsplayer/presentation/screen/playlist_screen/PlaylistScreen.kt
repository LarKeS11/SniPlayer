package com.example.lrsplayer.presentation.screen.playlist_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lrsplayer.R
import com.example.lrsplayer.domain.model.Playlist
import com.example.lrsplayer.presentation.navigation.Screen
import com.example.lrsplayer.presentation.theme.sf_pro_text
import com.example.lrsplayer.until.ThemeColors

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlaylistScreen(
    colors: ThemeColors,
    navController: NavController,
    viewModel: PlaylistViewModel = hiltViewModel(),
) {
    
    val state by viewModel.state.collectAsState()

    val dialogActive by viewModel.dialogActive.collectAsState()

    if(dialogActive) {
        NewPlaylistAlertDialog(
            colors = colors,
            onSubmit = {name, uri ->
                viewModel.setDialogActive(false)
                viewModel.createNewPlaylist(name = name, uri = uri)
                       },
            onDismiss = {
                viewModel.setDialogActive(false)
            }
        )
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 9.dp)
    ) {
        
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            item { 
                Spacer(modifier = Modifier.height(40.dp))
            }
            itemsIndexed(state.playlists){index, item ->

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    item.forEach { playlist ->
                        Card(
                            modifier = Modifier
                                .weight(0.2f)
                                .height(170.dp),
                            backgroundColor = colors.playlist_card,
                            shape = RoundedCornerShape(12.dp),
                            elevation = 5.dp,
                            onClick = {
                                navController.navigate(Screen.PlaylistMusicsScreen.withArgs(playlist.id.toString()))
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 16.dp)
                            ) {

                                Text(
                                    text = playlist.name,
                                    fontFamily = sf_pro_text,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colors.title,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                AsyncImage(
                                    model = playlist.imageFile,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.BottomCenter)
                                        .height(105.dp)
                                        .alpha(0.5f),
                                    contentScale = ContentScale.Crop
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(bottom = 9.dp, end = 13.dp),
                                    contentAlignment = Alignment.BottomEnd
                                ) {
                                    IconButton(
                                        onClick = {  }
                                    ) {

                                        Icon(
                                            painter = painterResource(id = R.drawable.play_button),
                                            contentDescription = "",
                                            modifier = Modifier.size(42.dp),
                                            tint = colors.title
                                        )

                                    }
                                }
                            }
                        }
                    }

                }
                Spacer(modifier = Modifier.height(10.dp))

            }
            
            item { 
                Spacer(modifier = Modifier.height(200.dp))
            }
            
        }

        Box(
            modifier = Modifier
                .padding(bottom = 80.dp, end = 34.dp)
                .align(Alignment.BottomEnd)
        ) {
            IconButton(
                onClick = {
                    viewModel.setDialogActive(true)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.__icon__add_circle_),
                    contentDescription = "",
                    modifier = Modifier.size(58.dp),
                    tint = colors.title
                )
            }
        }

    }
    
}