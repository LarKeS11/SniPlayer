package com.example.lrsplayer.presentation.screen.playlist_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lrsplayer.R
import com.example.lrsplayer.until.ThemeColors

@Composable
fun PlaylistScreen(
    colors: ThemeColors,
    viewModel: PlaylistViewModel = hiltViewModel(),
) {

    val dialogActive by viewModel.dialogActive.collectAsState()

    if(dialogActive) {
        NewPlaylistAlertDialog(
            colors = colors,
            onSubmit = { viewModel.setDialogActive(false) },
            onDismiss = { viewModel.setDialogActive(false) }
        )
    }
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .padding(bottom = 80.dp, end = 34.dp)
                .align(Alignment.BottomEnd)
        ) {
            IconButton(
                onClick = {
                    viewModel.setDialogActive(true)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.__icon__add_circle_),
                    contentDescription = "",
                    modifier = Modifier.size(58.dp),
                    tint = colors.title
                )
            }
        }

    }
    
}