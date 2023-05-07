package com.example.lrsplayer.presentation.screen.sign_up_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lrsplayer.presentation.navigation.Screen
import com.example.lrsplayer.presentation.screen.sign_in_screen.SignInViewModel
import com.example.lrsplayer.presentation.theme.sf_pro_text
import com.example.lrsplayer.presentation.views.*
import com.example.lrsplayer.until.ThemeColors

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel(),
    colors: ThemeColors
) {

    val state by viewModel.state.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.main_background)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        item{
            Text(
                text = "Sign up now",
                color = colors.title,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = sf_pro_text
            )
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Please fill the details and create account",
                color = colors.sub_title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = sf_pro_text,
                textAlign = TextAlign.Center
            )
        }
        item {
            Spacer(modifier = Modifier.height(25.dp))
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                TextFieldView(
                    text = state.name,
                    colors = colors,
                    modifier = Modifier.fillMaxWidth(),
                    hint = "Name",
                    placeholder = "Enter your name"
                ){
                    viewModel.changeName(it)
                }
                TextFieldView(
                    text = state.login,
                    colors = colors,
                    modifier = Modifier.fillMaxWidth(),
                    hint = "Email",
                    placeholder = "Enter your email"
                ){
                    viewModel.changeLogin(it)
                }
                PasswordFieldView(
                    pass = state.password,
                    colors = colors,
                    hint = "Password",
                    placeholder = "Enter password",
                    modifier = Modifier.fillMaxWidth()
                ){
                    viewModel.changePassword(it)
                }
            }
        }
        item{
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                HighLightClickableText(text = "Sign in") {
                    navController.navigate(Screen.SignInScreen.route)
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(40.dp))
            PrimaryButton("Sign Up"){

            }
        }
        item {
            Spacer(modifier = Modifier.height(40.dp))
            GoogleButton(colors){

            }
        }
    }

}