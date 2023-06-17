package com.example.lrsplayer.presentation.screen.music_screen

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lrsplayer.R
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.presentation.theme.sf_pro_text
import com.example.lrsplayer.presentation.views.MusicProgressBar
import com.example.lrsplayer.presentation.views.SettingIcon
import com.example.lrsplayer.until.SmallService
import com.example.lrsplayer.until.ThemeColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


//Log.d("dfgdfgdfg",currentDuration)


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MusicControl(
    music:Music,
    musicImage: Bitmap?,
    showControlScreen:Boolean,
    colors: ThemeColors,
    pause:Boolean,
    musicLoop:Boolean,
    deleteMusic:() -> Unit,
    shuffleMusic:()->Unit,
    switchMusicLoop:() -> Unit,
    switchControlScreenVisible:()->Unit,
    setMusicPosition:(Float) -> Unit,
    musicDuration:() -> String,
    getCurrentPos:() -> Int,
    getMusicPercProgress:() -> Float,
    onClose:() -> Unit,
    onActive:() -> Unit,
    onNext:() -> Unit,
    onLast:() -> Unit
) {


    val showSettingMenu = remember {
        mutableStateOf(false)
    }


    val currentPosition = remember {
        mutableStateOf("00:00")
    }
    val duration = remember {
        mutableStateOf("")
    }

    val percProgress = remember {
        mutableStateOf(0f)
    }


    LaunchedEffect(Unit){
        currentPosition.value = SmallService.convertMillisecondsToTimeString(getCurrentPos() - 1000)
    }

    LaunchedEffect(pause,musicLoop){

        if(!pause){
            while(true){
                percProgress.value = getMusicPercProgress()
                duration.value = musicDuration()
                currentPosition.value = SmallService.convertMillisecondsToTimeString(getCurrentPos())
                if(musicDuration() == SmallService.convertMillisecondsToTimeString(getCurrentPos()) && !musicLoop){
                    duration.value = ""
                    onNext()
                }
                delay(200)
            }
        }
    }

    @Composable
    fun MusicImage(
        modifier: Modifier = Modifier
    ){
        if(musicImage == null) {
            Image(
                painter = painterResource(id = R.drawable.no_music_image),
                contentDescription = "",
                modifier = modifier,
                contentScale = ContentScale.Crop
            )
        }else{
            Image(
                musicImage.asImageBitmap(),
                contentDescription = "",
                modifier = modifier,
                contentScale = ContentScale.Crop
            )
        }
    }
    Log.d("sdfgsdfsdf", "###############")
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if(showControlScreen){
            Box(modifier = Modifier
                .fillMaxSize()
                .background(colors.main_background)
            ){

                MusicImage(modifier = Modifier.fillMaxSize())

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.main_background.copy(alpha = 0.94f))
                )


                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    onClose()
                                },
                                modifier = Modifier.size(29.dp)
                            ) {
                                Icon(
                                    painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                                    contentDescription = "",
                                    modifier = Modifier.size(29.dp)
                                )
                            }
                            SettingIcon(colors = colors) {
                                showSettingMenu.value = !showSettingMenu.value
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(45.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 45.dp)
                        ){
                            MusicImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(302.dp)
                                    .clip(RoundedCornerShape(26.dp))
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(40.dp))
                        Text(
                            text = if(music.name.length > 20)music.name.substring(0,19) + "..." else music.name,
                            fontFamily = sf_pro_text,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 23.sp,
                            color = colors.title,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = music.author?: "no author",
                            fontFamily = sf_pro_text,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp,
                            color = colors.title,
                            textAlign = TextAlign.Center
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(30.dp))
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 42.dp)
                                .align(Alignment.BottomCenter),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(52.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        shuffleMusic()
                                    },
                                    modifier = Modifier.size(20.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.__icon__shuffle_),
                                        contentDescription = "",
                                        modifier = Modifier.size(20.dp),
                                        tint = colors.title
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        switchMusicLoop()
                                    },
                                    modifier = Modifier
                                        .size(20.dp)
                                        .background(if (musicLoop) Color.Gray else Color.Transparent)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_repeat_24),
                                        contentDescription = "",
                                        modifier = Modifier.size(20.dp),
                                        tint = colors.title
                                    )
                                }
                                IconButton(
                                    onClick = {  },
                                    modifier = Modifier.size(20.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.unfilledheart),
                                        contentDescription = "",
                                        modifier = Modifier.size(20.dp),
                                        tint = colors.title
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            MusicProgressBar(
                                progress = percProgress.value,
                                colors = colors,
                                modifier = Modifier.fillMaxWidth()
                            ){progress ->
                                setMusicPosition(progress)
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = currentPosition.value,
                                    fontFamily = sf_pro_text,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 11.sp,
                                    color = colors.title
                                )
                                Text(
                                    text = duration.value,
                                    fontFamily = sf_pro_text,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 11.sp,
                                    color = colors.title
                                )
                            }

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(30.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        duration.value = ""
                                        onLast()
                                    },
                                    modifier = Modifier.size(34.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.last_button),
                                        contentDescription = "",
                                        modifier = Modifier.size(34.dp),
                                        tint = colors.title
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        onActive()
                                    },
                                    modifier = Modifier.size(34.dp)
                                ) {

                                    Icon(
                                        painter = painterResource(id =if(pause) R.drawable.play else R.drawable.pause_button),
                                        contentDescription = "",
                                        modifier = Modifier.size(34.dp),
                                        tint = colors.title
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        duration.value = ""
                                        onNext()
                                    },
                                    modifier = Modifier.size(34.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.next_button),
                                        contentDescription = "",
                                        modifier = Modifier.size(34.dp),
                                        tint = colors.title
                                    )
                                }
                            }
                        }
                    }

                }

                if(showSettingMenu.value){
                    Box(
                        modifier = Modifier.align(Alignment.TopEnd).padding(top = 55.dp, end = 25.dp)
                    ) {
                        SettingMenu(
                            colors = colors,
                            onRemove = {
                                showSettingMenu.value = !showSettingMenu.value
                                deleteMusic()
                            }
                        )
                    }
                }


            }
        }else{
            Box(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                SmallMusicPanel(
                    music = music,
                    musicImage = {
                        MusicImage(it)
                    },
                    pause = pause,
                    progress = percProgress.value,
                    colors = colors,
                    onProgressChange = { progress ->
                        setMusicPosition(progress)
                    },
                    onNext = { onNext() },
                    onLast = { onLast() },
                    onPause = { onActive() },
                    onClick = {
                        switchControlScreenVisible()
                    }
                )
            }

        }

    }


}