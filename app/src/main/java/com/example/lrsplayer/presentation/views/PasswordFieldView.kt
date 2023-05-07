package com.example.lrsplayer.presentation.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.lrsplayer.R
import com.example.lrsplayer.until.ThemeColors

@Composable
fun PasswordFieldView(
    pass:String,
    colors: ThemeColors,
    modifier: Modifier = Modifier,
    placeholder:String = "",
    hint:String = "",
    singleLine:Boolean = true,
    onChange:(String) -> Unit
) {

    var passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = pass,
        onValueChange = {onChange(it)},
        placeholder = { Text(
            text = placeholder,
            color = colors.title.copy(alpha = 0.6f)
        )},
        label = { Text(
            text = hint,
            color = colors.title.copy(alpha = 0.6f)
        ) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible.value)
                R.drawable.ic_baseline_visibility_24
            else R.drawable.ic_baseline_visibility_off_24
            val description = if (passwordVisible.value) "Hide password" else "Show password"

            IconButton(onClick = {passwordVisible.value = !passwordVisible.value}){
                Icon(
                    painter = painterResource(id = image),
                    description,
                    tint = colors.title
                )
            }
        },
        modifier = modifier,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = colors.second_main_background,
            cursorColor = colors.title,
            textColor = colors.title,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(14.dp)
    )

}