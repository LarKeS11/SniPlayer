package com.example.lrsplayer.presentation.screen.playlist_musics_screen

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.lrsplayer.R
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.presentation.screen.views.DialogWrapper
import com.example.lrsplayer.presentation.screen.views.MusicItem
import com.example.lrsplayer.presentation.screen.views.MusicTextField
import com.example.lrsplayer.presentation.theme.sf_pro_text
import com.example.lrsplayer.presentation.views.MusicButton
import com.example.lrsplayer.presentation.views.PrimaryButton
import com.example.lrsplayer.until.ThemeColors
import kotlinx.coroutines.launch

@Composable
fun PlaylistMusicsScreen(
    colors: ThemeColors,
    navController: NavController,
    musics:List<Music>,
    getMusics:() -> Unit,
    setCurrentMusic:(Int) -> Unit,
    viewModel: PlaylistMusicScreenViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    val editMode by viewModel.editMode.collectAsState()
    val playlistName by viewModel.playlistName.collectAsState()
    val alertDialogMode by viewModel.alertDialogMode.collectAsState()
    val musicNotInPlaylist = viewModel.allMusics
    val selectedMusics by viewModel.selectedMusicsState.collectAsState()
    val selectingMode by viewModel.selectingModeState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = musics, block = {
        viewModel.getMusicsNotInPlaylist(musics)
    })

    LaunchedEffect(key1 = Unit, block = {
        getMusics()
    })


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .padding(start = 20.dp, end = 35.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Box(
                    modifier = Modifier
                        .padding(top = 3.dp)
                ) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .width(30.dp)
                            .height(25.dp)
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "",
                            modifier = Modifier
                                .width(30.dp)
                                .height(25.dp),
                            tint = colors.title
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!editMode) {
                        if (state.playlist != null) {
                            Text(
                                text = state.playlist!!.name,
                                color = colors.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = sf_pro_text
                            )
                        }
                        IconButton(
                            onClick = {
                                viewModel.switchEditMode()
                            },
                            modifier = Modifier
                                .size(18.dp)
                        ) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(18.dp),
                                tint = colors.title
                            )
                        }
                        if (selectedMusics.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    viewModel.deleteAllSelectedMusics() {
                                        getMusics()
                                    }
                                },
                                modifier = Modifier.size(23.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "",
                                    tint = colors.title,
                                    modifier = Modifier.size(23.dp)
                                )
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.width(8.dp))
                        MusicTextField(
                            text = playlistName, colors = colors, modifier = Modifier
                                .height(22.dp)
                                .fillMaxWidth(0.72f)
                        ) {
                            viewModel.editPlaylistName(it)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(5f),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    viewModel.changePlaylistName()
                                },
                                modifier = Modifier.size(23.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "",
                                    tint = colors.title,
                                    modifier = Modifier.size(23.dp)
                                )
                            }
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        viewModel.deletePlaylist()
                                        navController.popBackStack()
                                    }
                                },
                                modifier = Modifier.size(23.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "",
                                    tint = colors.title,
                                    modifier = Modifier.size(23.dp)
                                )
                            }
                        }
                    }
                }
                if(!editMode){
                    Box(
                        modifier = Modifier.padding(top = 3.dp)
                    ) {
                        MusicButton(
                            icon = R.drawable.baseline_add_circle_outline_24,
                            colors = colors,
                            modifier = Modifier.size(23.dp)
                        ) {
                            viewModel.switchAlertDialogMode(true)
                        }
                    }
                }
            }
        }
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 22.dp)
        ){

            item {
                if(alertDialogMode){
                    DialogWrapper(
                        modifier = Modifier
                            .width(284.dp),
                        colors = colors,
                        onDismiss = {
                            viewModel.resetSelectedMusic()
                            viewModel.switchAlertDialogMode(false)
                            getMusics()
                        }
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp, bottom = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Select musics:",
                                fontFamily = sf_pro_text,
                                fontWeight = FontWeight.SemiBold,
                                color = colors.title,
                                fontSize = 16.5.sp
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            LazyColumn(modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(200.dp, 400.dp)
                                .padding(end = 20.dp)){
                                itemsIndexed(musicNotInPlaylist){index, item ->
                                    MusicItemWithCheckBox(
                                        music = item,
                                        colors = colors,
                                        image = viewModel.getMusicImage(item.path),
                                        selected = item.id!! in viewModel.currentSelectedMusics
                                    ){
                                        if(item.id !in viewModel.currentSelectedMusics) viewModel.addNewPlaylistMusic(item.id)
                                        else viewModel.removePlaylistMusic(item.id)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            PrimaryButton(
                                text = "Add",
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(40.dp),
                                textStyle = TextStyle(
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontFamily = sf_pro_text,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                viewModel.addAllNewMusics() {
                                    getMusics()
                                }
                                viewModel.switchAlertDialogMode(false)
                            }
                        }
                    }
                }
            }

           itemsIndexed(musics){index, item ->

               MusicItem(
                   music = item,
                   colors = colors,
                   image = viewModel.getMusicImage(item.path),
                   isSelected = item in selectedMusics,
                   onLongClick = {
                       if (!selectingMode) {
                           viewModel.setSelectingMode(true)
                           viewModel.selectNewMusic(item)
                       }
                   }
               ) {
                   if (selectingMode) {
                       if (item !in selectedMusics) viewModel.selectNewMusic(item)
                       else {
                           if (selectedMusics.size == 1) viewModel.setSelectingMode(false)
                           viewModel.unSelectMusic(item)

                       }
                   }

                   if (!selectingMode) setCurrentMusic(index)
               }

           }
        }

    }
    
}


@Composable
fun MusicItemWithCheckBox(music: Music, colors: ThemeColors, image:Bitmap?,selected:Boolean, onClick:() -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MusicItem(
            music = music,
            colors = colors,
            modifier = Modifier.weight(12f),
            image = image
        ) {
            onClick()
        }
        Icon(
            imageVector = Icons.Default.Done,
            contentDescription = "",
            tint = colors.title,
            modifier = Modifier
                .weight(1f)
                .alpha(if (selected) 1f else 0f)
        )
    }
}
