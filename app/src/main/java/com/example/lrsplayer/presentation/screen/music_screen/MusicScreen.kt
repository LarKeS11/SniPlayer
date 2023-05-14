package com.example.lrsplayer.presentation.screen.music_screen

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lrsplayer.R
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
        modifier = Modifier.fillMaxSize(),
        
    ) {
        
        items(state.data, {it.id!!}){
            Text(text = it.name)
        }

        item {
            Button(onClick = {
                launcher.launch("audio/*")
            }) {
                Text(text = "click")
            }  
        }
    }

    
}