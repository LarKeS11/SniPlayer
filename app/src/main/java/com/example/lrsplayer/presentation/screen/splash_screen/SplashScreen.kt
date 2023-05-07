package com.example.lrsplayer.presentation.screen.splash_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lrsplayer.presentation.navigation.Screen
import com.example.lrsplayer.presentation.theme.sf_pro_text
import com.example.lrsplayer.until.ThemeColors

@Composable
fun SplashScreen(
    navController: NavController,
    colors: ThemeColors
) {

    LaunchedEffect(Unit){
        navController.navigate(Screen.SignInScreen.route)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.main_background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "Wait while loading..",
                color = colors.title,
                fontFamily = sf_pro_text,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            CircularProgressIndicator()
        }

    }
}