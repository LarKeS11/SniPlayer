package com.example.lrsplayer.presentation.screen.sign_in_screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lrsplayer.presentation.navigation.Screen
import com.example.lrsplayer.presentation.theme.sf_pro_text
import com.example.lrsplayer.presentation.views.*
import com.example.lrsplayer.until.ThemeColors
import com.google.android.gms.auth.api.signin.GoogleSignIn

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel(),
    colors: ThemeColors
) {

    val state by viewModel.state.collectAsState()

    val hasBeenAuthorizated by viewModel.hasBeenAuthorizated.collectAsState()

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        val account = task.result
        if(account != null) {
            viewModel.makeRequest(account.idToken!!)
        }
    }

    LaunchedEffect(hasBeenAuthorizated){
        if(hasBeenAuthorizated){
            navController.navigate(Screen.SplashScreen.route)
        }
    }



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
                text = "Sign in now",
                color = colors.title,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = sf_pro_text
            )
        }
        item { 
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Please sign in to continue our app",
                color = colors.sub_title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = sf_pro_text
            )
        }
        item {
            Spacer(modifier = Modifier.height(25.dp))
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
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
                HighLightClickableText(text = "Sign up now") {
                    navController.navigate(Screen.SignUpScreen.route)
                }
            }
        }
        item { 
            Spacer(modifier = Modifier.height(40.dp))
            PrimaryButton("Sign In"){

            }
        }
        item {
            Spacer(modifier = Modifier.height(40.dp))
            GoogleButton(colors){
                launcher.launch(viewModel.getSignInClient().signInIntent)
            }
        }
    }

}