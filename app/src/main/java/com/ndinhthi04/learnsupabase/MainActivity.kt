package com.ndinhthi04.learnsupabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ndinhthi04.learnsupabase.Authencation.LoadingComponent
import com.ndinhthi04.learnsupabase.Authencation.SupabaseAuthViewModel
import com.ndinhthi04.learnsupabase.Authencation.data.model.UseState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainScreen(
        viewModel: SupabaseAuthViewModel = viewModel(),
    ) {
        val context = LocalContext.current
        val userState by viewModel.userState

        var userEmail by remember { mutableStateOf("") }
        var userPassword by remember { mutableStateOf("") }
        var currentUserState by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            viewModel.isUserLogin(context)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = userEmail,
                onValueChange = { userEmail = it },
                placeholder = { Text(text = "Enter Email") }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            TextField(
                value = userPassword,
                onValueChange = { userPassword = it },
                placeholder = { Text(text = "Enter Password") }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = {
                viewModel.SignUp(
                    context,
                    userEmail,
                    userPassword
                )
            }) {
                Text(text = "Sign Up")
            }
            Button(onClick = {
                viewModel.Login(
                    context,
                    userEmail,
                    userPassword
                )
            }) {
                Text(text = "Login")
            }
            Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                onClick = {
                    viewModel.logOut(context)
                }) {
                Text(text = "Log out")
            }


            when (userState) {
                is UseState.Loading -> {
                    LoadingComponent()
                }

                is UseState.Success -> {
                    val message = (userState as UseState.Success).message
                    currentUserState = message
                }

                is UseState.Error -> {
                    val message = (userState as UseState.Error).message
                    currentUserState = message
                }
            }

            if (currentUserState.isNotEmpty()) {
                Text(text = currentUserState)
            }
        }
    }

}
