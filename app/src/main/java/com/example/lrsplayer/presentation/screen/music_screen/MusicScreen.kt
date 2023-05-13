package com.example.lrsplayer.presentation.screen.music_screen

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lrsplayer.until.ThemeColors

@Composable
fun MusicScreen(
    colors:ThemeColors,
    viewModel: MusicViewModel = hiltViewModel(),
    navController: NavController
) {

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){res ->
        Log.d("sdfsdfsdf", viewModel.getAudioFile(uri = res!!).toString())
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Button(onClick = {
            launcher.launch("audio/*")
        }) {
            Text(text = "click")
        }

    }

    
}