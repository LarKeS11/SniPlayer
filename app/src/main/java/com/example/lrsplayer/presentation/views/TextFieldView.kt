package com.example.lrsplayer.presentation.views

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.lrsplayer.presentation.navigation.Screen
import com.example.lrsplayer.until.ThemeColors

@Composable
fun TextFieldView(
    text:String,
    colors: ThemeColors,
    modifier: Modifier = Modifier,
    placeholder:String = "",
    hint:String = "",
    singleLine:Boolean = true,
    onChange:(String) -> Unit
) {

    OutlinedTextField(
        value = text,
        onValueChange = {
            onChange(it)
        },
        placeholder = { Text(
            text = placeholder,
            color = colors.title.copy(alpha = 0.6f)
        )},
        label = { Text(
            text = hint,
            color = colors.title.copy(alpha = 0.6f)
        ) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = modifier,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = colors.second_main_background,
            cursorColor = colors.title,
            unfocusedIndicatorColor = Color.Transparent,
            textColor = colors.title
        ),
        shape = RoundedCornerShape(14.dp),
        singleLine = singleLine
    )

}