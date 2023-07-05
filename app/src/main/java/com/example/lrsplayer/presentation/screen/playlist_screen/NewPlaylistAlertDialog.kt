package com.example.lrsplayer.presentation.screen.playlist_screen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.lrsplayer.R
import com.example.lrsplayer.presentation.screen.views.DialogWrapper
import com.example.lrsplayer.presentation.screen.views.MusicTextField
import com.example.lrsplayer.presentation.theme.sf_pro_text
import com.example.lrsplayer.until.ThemeColors
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun NewPlaylistAlertDialog(
    colors:ThemeColors,
    onSubmit:(String, Uri?) -> Unit,
    onDismiss:() -> Unit
) {



    val playlistName = remember {
        mutableStateOf("")
    }

    val selectedImageUri = remember {
        mutableStateOf<Uri?>(null)
    }


    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if(uri != null) selectedImageUri.value = uri
        }

    DialogWrapper(
        colors = colors,
        onDismiss = {
            onDismiss()
        },
        modifier = Modifier
            .width(284.dp)
            .height(200.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 15.dp)
                .padding(start = 24.dp, end = 33.dp)
        ) {

            Text(
                text = "New Playlist...",
                fontSize = 17.sp,
                fontFamily = sf_pro_text,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(35.dp))
            Row() {
                Button(
                    modifier = Modifier.size(56.dp),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = {
                        galleryLauncher.launch("image/*")
                    },
                    elevation = ButtonDefaults.elevation(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colors.image_picker_color)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if(selectedImageUri.value != null){
                            AsyncImage(
                                model = selectedImageUri.value,
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }else{
                            Box(modifier = Modifier
                                .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.__icon__camera_),
                                    contentDescription = "",
                                    modifier = Modifier.size(30.dp),
                                    tint = colors.title
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.width(25.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Text(
                        text = "Playlist name",
                        color = colors.title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = sf_pro_text
                    )
                    MusicTextField(
                        text = playlistName.value,
                        colors = colors,
                        modifier = Modifier
                            .width(160.dp)
                            .height(22.dp)
                    ){
                        playlistName.value = it
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.padding(bottom = 20.dp)
                ) {

                    Button(
                        onClick = {
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        contentPadding = PaddingValues(0.dp),
                        elevation = ButtonDefaults.elevation(0.dp)
                    ) {
                        Text(
                            text = "Cancel",
                            style = TextStyle(
                                color = colors.title,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = sf_pro_text
                            )
                        )
                    }
                    Button(
                        onClick = {
                            onSubmit(
                                playlistName.value,
                                selectedImageUri.value
                            )
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        contentPadding = PaddingValues(0.dp),
                        elevation = ButtonDefaults.elevation(0.dp)
                    ) {
                        Text(
                            text = "Create",
                            style = TextStyle(
                                color = colors.title,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = sf_pro_text
                            )
                        )
                    }

                }

            }

        }
    }

}