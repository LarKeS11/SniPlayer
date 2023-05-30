package com.example.lrsplayer.presentation.navigation

sealed class Screen(val route:String) {
    object MusicScreen: Screen("music_screen")
    object SettingScreen: Screen("setting_screen")
    object SignInScreen: Screen("sign_in_screen")
    object SignUpScreen:Screen("sign_up_screen")
    object SplashScreen:Screen("splash_screen")
    object MusicControlScreen:Screen("music_control_screen")

    fun withArgs(vararg args: String):String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

}