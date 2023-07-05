package com.example.lrsplayer.presentation.screen.music_screen

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lrsplayer.R
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.presentation.screen.views.MusicItem
import com.example.lrsplayer.presentation.theme.sf_pro_text
import com.example.lrsplayer.presentation.views.MusicButton
import com.example.lrsplayer.presentation.views.PlayingView
import com.example.lrsplayer.until.ThemeColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun MusicScreen(
    colors:ThemeColors,
    viewModel: MusicViewModel = hiltViewModel(),
    listOfMusic:List<Music> = listOf(),
    appContext:Context,
    setCurrentMusic:(Int)-> Unit,
    updateMusics:() -> Unit,
    navController: NavController
) {

  //  Log.d("sdfsdfsdfds","#########")


    val state by viewModel.state.collectAsState()
    val musicHasUpdated by viewModel.musicHasUpdated.collectAsState()

    LaunchedEffect(musicHasUpdated){
        updateMusics()
    }

    LaunchedEffect(listOfMusic){
        viewModel.uploadMusics(listOfMusic)
    }


    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.main_background)
        ) {

            item {
                Spacer(modifier = Modifier.height(25.dp))
            }


            itemsIndexed(state.data){index, it ->
                MusicItem(
                    music = it,
                    colors = colors,
                    image = viewModel.getMusicImage(it.path)
                ) {
                    setCurrentMusic(index)
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
            item { 
                Spacer(modifier = Modifier.height(40.dp))
            }
        }


    }

    
}


