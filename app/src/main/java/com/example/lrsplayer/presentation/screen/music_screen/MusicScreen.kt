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



    val state by viewModel.state.collectAsState()
    val showControlScreen by viewModel.showControlScreen.collectAsState()
    val musicTransition by viewModel.musicTransition.collectAsState()
    val showSearchBar by viewModel.showSearchBar.collectAsState()
    val musicHasUpdated by viewModel.musicHasUpdated.collectAsState()

    LaunchedEffect(musicHasUpdated){
        updateMusics()
    }

    LaunchedEffect(listOfMusic){
        viewModel.uploadMusics(listOfMusic)
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { res ->
        viewModel.saveMusic(res!!)
    }


    Log.d("sdfgdsfgsdf",state.isLooping.toString())


    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.main_background)
                .padding(top = 24.dp)
        ) {
            item {
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
                                        tint = colors.title
                                    )
                                }
                                Text(
                                    text = "SniPlayer",
                                    fontSize = 20.sp,
                                    fontFamily = sf_pro_text,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = colors.title
                                )
                            }
                        }else{
                            Box(
                                modifier = Modifier.padding(start = 20.dp)
                            ) {
                                SearchBar(
                                    text = state.searchText,
                                    colors = colors,
                                    onChange = {
                                        viewModel.setSearchText(it)
                                    }
                                )
                            }

                        }
                        IconButton(
                            onClick = {
                                      viewModel.switchSearchBar()
                            },
                            modifier = Modifier
                                .size(30.dp)
                        ) {
                            Icon(
                              if(!showSearchBar) Icons.Default.Search else Icons.Default.Close,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(30.dp),
                                tint = colors.title
                            )
                        }

                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 17.dp)
                        .padding(top = 30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(25.dp)
                    ) {
                        Text(
                            text = "Songs",
                            fontSize = 13.sp,
                            color = colors.title,
                            fontFamily = sf_pro_text,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Playlist",
                            fontSize = 13.sp,
                            color = colors.title,
                            fontFamily = sf_pro_text,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Albums",
                            fontSize = 13.sp,
                            color = colors.title,
                            fontFamily = sf_pro_text,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MusicButton(
                            icon = R.drawable.__icon__shuffle_,
                            colors = colors,
                            modifier = Modifier
                                .width(22.dp)
                                .height(20.dp)
                        ){
                            if (!musicTransition) {
//                                viewModel.setMusicTransition(true)
//                                viewModel.shuffleMusic()
//                                viewModel.switchControlScreenState()
                            }
                        }
                        MusicButton(
                            icon = R.drawable.musicfilter,
                            colors = colors,
                            modifier = Modifier.size(22.dp)
                        ){

                        }
                        MusicButton(
                            icon = R.drawable.baseline_add_circle_outline_24,
                            colors = colors,
                            modifier = Modifier.size(23.dp)
                        ){
                            launcher.launch("audio/*")
                        }
                    }

                }
            }

            item {
                Spacer(modifier = Modifier.height(25.dp))
            }

            Log.d("dgdfgdfg",state.data.size.toString())

            itemsIndexed(state.data){index, it ->
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        setCurrentMusic(index)
                    },
                    elevation = ButtonDefaults.elevation(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 17.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            val img = viewModel.getMusicImage(it.path)
                            if(img == null){
                                Image(
                                     painterResource(id = R.drawable.no_music_image),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(RoundedCornerShape(6.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            } else{
                                Image(
                                    viewModel.getMusicImage(it.path)!!.asImageBitmap(),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(RoundedCornerShape(6.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(
                                    text = it.name,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = sf_pro_text,
                                    color = colors.title,
                                    modifier = Modifier.width(200.dp)
                                )
                                Text(
                                    text = it.author ?: "no author",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = sf_pro_text,
                                    color = colors.title
                                )
                            }
                        }
                        if(state.currentMusic!= null && index == state.currentMusic!!) PlayingView(pause = state.musicPause, colors = colors)
                    }

                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        Log.d("dfgfgdfg","there")



    }

    
}


