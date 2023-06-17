package com.example.lrsplayer.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.presentation.screen.music_control_screen.MusicControlScreen
import com.example.lrsplayer.presentation.screen.music_screen.MusicScreen
import com.example.lrsplayer.presentation.screen.sign_in_screen.SignInScreen
import com.example.lrsplayer.presentation.screen.sign_up_screen.SignUpScreen
import com.example.lrsplayer.presentation.screen.splash_screen.SplashScreen
import com.example.lrsplayer.until.ThemeColors
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun Navigate(
    navController: NavHostController,
    appContext:Context,
    colors:MutableStateFlow<ThemeColors>,
    listOfMusics:List<Music> = listOf(),
    setCurrentMusic:(Int) -> Unit,
    updateMusics:() -> Unit,
    setTheme:(String) -> Unit
) {

    val appColors by colors.collectAsState()

    NavHost(navController = navController, startDestination = Screen.MusicScreen.route){

        composable(
            route = Screen.SplashScreen.route
        ){entry ->
            SplashScreen(
                navController = navController,
                colors = appColors
            )
        }

        composable(
            route = Screen.SignInScreen.route
        ){
            SignInScreen(navController = navController, colors = appColors)
        }

        composable(
            route = Screen.SignUpScreen.route
        ){
            SignUpScreen(navController = navController, colors = appColors)
        }

        composable(
            route = Screen.MusicScreen.route
        ){
            MusicScreen(
                colors = appColors,
                navController = navController,
                appContext = appContext,
                setCurrentMusic = {setCurrentMusic(it)},
                updateMusics = {
                    updateMusics()
                },
                listOfMusic = listOfMusics
            )
        }

        composable(
            route = Screen.MusicControlScreen.route
        ){
            MusicControlScreen(id = 12, colors = appColors)
        }

    }

}