package com.example.lrsplayer.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.lrsplayer.R
import com.example.lrsplayer.presentation.navigation.Navigate
import com.example.lrsplayer.presentation.navigation.Screen
import com.example.lrsplayer.presentation.screen.music_screen.MusicControl
import com.example.lrsplayer.presentation.screen.music_screen.SearchBar
import com.example.lrsplayer.presentation.theme.sf_pro_text
import com.example.lrsplayer.presentation.views.MusicButton
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
             val showSearchBar by viewmodel.showSearchBar.collectAsState()
             val showTopBar by viewmodel.showTopBar.collectAsState()

             val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { res ->
                 viewmodel.saveMusic(res!!)
                 viewmodel.getMusics()
             }

             LaunchedEffect(currentMusicIndex){
                 if(currentMusicIndex != null){
                     try {
                         viewmodel.stopMusic()
                     }catch (e:Exception){

                     }
                     viewmodel.playMusic(state.musics[currentMusicIndex!!], this@MainActivity)
                     viewmodel.setMusicLooping()
                 }
             }

             val appColors by viewmodel.currentMainThemeColors.collectAsState()
             Box(
                 modifier = Modifier
                     .fillMaxSize()
                     .padding(top = if (!showControlScreen && showTopBar) 94.dp else 0.dp)
             ) {
                 Navigate(
                     navController = navController,
                     colors = viewmodel.currentMainThemeColors,
                     appContext = this@MainActivity,
                     setCurrentMusic = {
                         viewmodel.switchControlScreen(true)
                         viewmodel.setCurrentMusic(it)
                     },
                     updateMusics = {
                         viewmodel.getMusics()
                     },
                     listOfMusics = state.musics,
                     showTopBar = {bool ->
                         viewmodel.switchShowingTopBar(bool)
                     }
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
                     switchMusicLoop = {
                         Log.d("dsadasd",viewmodel._isLooping.value.toString())
                         viewmodel.switchLooping()
                                       },
                     shuffleMusic = {
                         if (!musicTransition) {
                             viewmodel.setMusicTransition(true)
                             viewmodel.shuffleMusic()
                         }
                     },
                     switchControlScreenVisible = {viewmodel.switchControlScreen(true)},
                     setMusicPosition = { viewmodel.setMusicPosition(it) },
                     musicDuration = { viewmodel.getMusicDuration() },
                     getMusicPercProgress = { viewmodel.getMusicPercProgress() },
                     musicImage = viewmodel.getMusicImage(state.musics[currentMusicIndex!!].path),
                     getCurrentPos = { viewmodel.getCurrentMusicPosition() },
                     onClose = { viewmodel.switchControlScreen(false) },
                     onActive = {
                         if(!musicTransition){
                             viewmodel.setMusicTransition(true)
                             if (state.musicPause) viewmodel.continueMusic()
                             else viewmodel.pauseMusic()
                         }
                     },
                     deleteMusic = {
                         if (!musicTransition) {
                             viewmodel.setMusicTransition(true)
                             viewmodel.deleteMusic(this.applicationContext)
                         }

                     },
                     onNext = {
                         if (!musicTransition) {
                             viewmodel.setMusicTransition(true)
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


             if(!showControlScreen && showTopBar){
                 Box(
                     modifier = Modifier
                         .fillMaxSize()
                         .padding(top = 24.dp),
                     contentAlignment = Alignment.TopCenter
                 ) {

                     Column() {
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
                                 if(!showSearchBar){
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
                                                 tint = appColors.title
                                             )
                                         }
                                         Text(
                                             text = "SniPlayer",
                                             fontSize = 20.sp,
                                             fontFamily = sf_pro_text,
                                             fontWeight = FontWeight.ExtraBold,
                                             color = appColors.title
                                         )
                                     }
                                 }else{
                                     Box(
                                         modifier = Modifier.padding(start = 20.dp)
                                     ) {
                                         SearchBar(
                                             text = state.searchText,
                                             colors = appColors,
                                             onChange = {
                                                 viewmodel.setSearchText(it)
                                             }
                                         )
                                     }

                                 }
                                 IconButton(
                                     onClick = {
                                         viewmodel.switchSearchBar()
                                     },
                                     modifier = Modifier
                                         .size(30.dp)
                                 ) {
                                     Icon(
                                         if(!showSearchBar) Icons.Default.Search else Icons.Default.Close,
                                         contentDescription = "",
                                         modifier = Modifier
                                             .size(30.dp),
                                         tint = appColors.title
                                     )
                                 }

                             }
                         }
                         Row(
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .padding(horizontal = 17.dp)
                                 .padding(top = 14.dp),
                             horizontalArrangement = Arrangement.SpaceBetween,
                             verticalAlignment = Alignment.CenterVertically
                         ) {
                             Row(
                                 horizontalArrangement = Arrangement.spacedBy(25.dp)
                             ) {
                                 ClickableText(
                                     text = AnnotatedString("Songs"),
                                     style = TextStyle(
                                         fontSize = 13.sp,
                                         color = appColors.title,
                                         fontFamily = sf_pro_text,
                                         fontWeight = FontWeight.SemiBold
                                     )
                                 ){
                                    navController.navigate(Screen.MusicScreen.route)
                                 }
                                 ClickableText(
                                     text = AnnotatedString("Playlist"),
                                     style = TextStyle(
                                         fontSize = 13.sp,
                                         color = appColors.title,
                                         fontFamily = sf_pro_text,
                                         fontWeight = FontWeight.SemiBold
                                     )
                                 ){
                                     navController.navigate(Screen.PlaylistScreen.route)

                                 }
                                 ClickableText(
                                     text = AnnotatedString("Albums"),
                                     style = TextStyle(
                                         fontSize = 13.sp,
                                         color = appColors.title,
                                         fontFamily = sf_pro_text,
                                         fontWeight = FontWeight.SemiBold
                                     )
                                 ){

                                 }
                             }

                             Row(
                                 horizontalArrangement = Arrangement.spacedBy(10.dp),
                                 verticalAlignment = Alignment.CenterVertically
                             ) {
                                 MusicButton(
                                     icon = R.drawable.__icon__shuffle_,
                                     colors = appColors,
                                     modifier = Modifier
                                         .width(22.dp)
                                         .height(20.dp)
                                 ){
                                     if (!musicTransition) {
                                         viewmodel.setMusicTransition(true)
                                         viewmodel.shuffleMusic()
                                         viewmodel.switchControlScreen(true)
                                     }
                                 }
                                 MusicButton(
                                     icon = R.drawable.musicfilter,
                                     colors = appColors,
                                     modifier = Modifier.size(22.dp)
                                 ){

                                 }
                                 MusicButton(
                                     icon = R.drawable.baseline_add_circle_outline_24,
                                     colors = appColors,
                                     modifier = Modifier.size(23.dp)
                                 ){
                                     launcher.launch("audio/*")
                                 }
                             }

                         }
                     }
                 }
             }
        }
    }
}