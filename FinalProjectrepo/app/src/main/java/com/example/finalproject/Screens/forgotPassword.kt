package com.example.finalproject.Screens

import android.util.Log
import android.widget.Button
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun forgotPassword(authenticationViewModel: AuthenticationViewModel,onPop:()->Unit){
    var email by remember{mutableStateOf("")}
    var scope = rememberCoroutineScope()
    var snackbarHostState = remember { SnackbarHostState() }
    var focus= LocalFocusManager.current

    Scaffold( snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) {it->
        Column(modifier= Modifier.padding(it)
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(value = email, onValueChange = { email = it },label={Text("Enter your Email address")},
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done))
            Spacer(modifier=Modifier.height(15.dp))
            ElevatedButton(onClick = {
                focus.clearFocus()
                authenticationViewModel.resetPassword(email,onSuccess={
                    scope.launch {
                        snackbarHostState.showSnackbar("Reset password link has been sent to your mail.",duration=SnackbarDuration.Long)

                    }
                    onPop()

                })
            }) {
                Text("Reset Password")
            }

        }
    }
}
