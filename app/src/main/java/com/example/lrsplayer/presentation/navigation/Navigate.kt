package com.example.lrsplayer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lrsplayer.presentation.screen.sign_in_screen.SignInScreen
import com.example.lrsplayer.presentation.screen.sign_up_screen.SignUpScreen
import com.example.lrsplayer.presentation.screen.splash_screen.SplashScreen
import com.example.lrsplayer.until.ThemeColors
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun Navigate(
    navController: NavHostController,
    colors:MutableStateFlow<ThemeColors>,
    setTheme:(String) -> Unit
) {

    val appColors by colors.collectAsState()

    NavHost(navController = navController, startDestination = Screen.SplashScreen.route){

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

    }

}