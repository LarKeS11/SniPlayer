package com.example.lrsplayer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.lrsplayer.presentation.navigation.Navigate
import com.example.lrsplayer.presentation.screen.music_screen.MusicControl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewmodel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContent {
             val navController = rememberNavController()
             val state by viewmodel.state.collectAsState()
             val currentMusicIndex by viewmodel.currentMusic.collectAsState()
             val showControlScreen by viewmodel.showControlScreen.collectAsState()
             val musicTransition by viewmodel.musicTransition.collectAsState()


             val appColors by viewmodel.currentMainThemeColors.collectAsState()
             Box(modifier = Modifier.fillMaxSize()) {
                 Navigate(
                     navController = navController,
                     colors = viewmodel.currentMainThemeColors,
                     appContext = this@MainActivity
                 ){theme ->
                     viewmodel.switchMainThemeColors(theme)
                 }
             }
             if(currentMusicIndex != null){
                 MusicControl(
                     colors = appColors,
                     pause = state.musicPause,
                     music = state.musics[currentMusicIndex!!],
                     musicLoop = state.isLooping,
                     showControlScreen = showControlScreen,
                     switchMusicLoop = {viewmodel.switchLooping()},
                     shuffleMusic = {
                         if (!musicTransition) {
                             viewmodel.setMusicTransition(true)
                             viewmodel.shuffleMusic()
                         }
                     },
                     switchControlScreenVisible = {viewmodel.switchControlScreen()},
                     setMusicPosition = { viewmodel.setMusicPosition(it) },
                     musicDuration = { viewmodel.getMusicDuration() },
                     getMusicPercProgress = { viewmodel.getMusicPercProgress() },
                     musicImage = viewmodel.getMusicImage(state.musics[currentMusicIndex!!].path),
                     getCurrentPos = { viewmodel.getCurrentMusicPosition() },
                     onClose = { viewmodel.switchControlScreen() },
                     onActive = {
                         if (state.musicPause) viewmodel.continueMusic()
                         else viewmodel.pauseMusic()
                     },
                     deleteMusic = {
                         if (!musicTransition) {
                             viewmodel.setMusicTransition(true)
                             viewmodel.deleteMusic(this.applicationContext)
                         }

                     },
                     onNext = {
                         if (!musicTransition) {
                             viewmodel.stopMusic()
                             viewmodel.nextMusic()
                         }
                     },
                     onLast = {
                         if (!musicTransition) {
                             viewmodel.setMusicTransition(true)
                             viewmodel.stopMusic()
                             viewmodel.lastMusic()
                         }
                     }
                 )
             }
        }
    }
}