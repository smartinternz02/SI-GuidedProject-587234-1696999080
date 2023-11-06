package com.example.finalproject.Screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.finalproject.R
import com.example.finalproject.components.ButtonComponent
import com.example.finalproject.components.ClickableLoginTextComponent
import com.example.finalproject.components.DividerTextComponent
import com.example.finalproject.components.HeadingTextComponent
//import com.example.finalproject.components.LoginOrRegister
import com.example.finalproject.components.MyTextField
import com.example.finalproject.components.NormalTextComponent
import com.example.finalproject.components.PasswordTextField
import com.example.finalproject.components.UnderlinedTextComponent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

//import com.example.finalproject.components.UnderlinedTextComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(authViewmodel: AuthenticationViewModel,onNavigate:()->Unit,auth: FirebaseAuth,navController:NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var validateEmail by remember { mutableStateOf(false) }
    var validatePassword by remember { mutableStateOf(false) }
    var basecontext = LocalContext.current
    var focus = LocalFocusManager.current
    var loading by remember { mutableStateOf(false) }
    var snackbarHostState = remember { SnackbarHostState() }
    var scope = rememberCoroutineScope()

   Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) })
    {it->

        Box(modifier = Modifier.padding(20.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(rememberScrollState())
                , horizontalAlignment = Alignment.CenterHorizontally
            )
            {

                NormalTextComponent(value = "Hey there,")
                HeadingTextComponent(value = "Welcome Back")

                MyTextField(labelValue = "Email",
                    painterResource(id = R.drawable.user),
                    value = email,
                    onValueChange = { email = it })

                if(validateEmail){
                    Text("Enter valid Email id",color=Color.Red,textAlign= TextAlign.Left)
                }

                PasswordTextField(
                    labelValue = "Password",
                    painterResource(id = R.drawable.padlock),
                    value = password,
                    onValueChange = { password = it })
                if(validatePassword){
                    Text("Enter correct password",color=Color.Red,textAlign= TextAlign.Left)
                }

                UnderlinedTextComponent("Forgot password?",navController)
                Spacer(modifier = Modifier.height(10.dp))



                Button(
                    onClick = { /*navController.navigate("Survey") */

                        focus.clearFocus()
                        // Sign in success, update UI with the signed-in user's information
                        validateEmail =
                            !(email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z0-9.-]+\$")))
                        validatePassword =
                            !(password.matches(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")))

                        if (!validateEmail && !validatePassword) {

                           loading=true
                            auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener() { task ->
                                    if (task.isSuccessful) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Login Successful")
                                        }
                                        authViewmodel.CheckIsAdmin()
                                        onNavigate()

//                                  navController.navigate("Survey")
                                        loading = false
                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Log.w(TAG, "signInWithEmail:failure", task.exception)

                                            Toast.makeText(
                                                basecontext,
                                                "Authentication failed.",
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                            scope.launch {
                                                snackbarHostState.showSnackbar("Enter valid Email id or Password")
                                            }

                                        loading = false
                                    }
                                }

                        }


                    },
                    modifier = Modifier,
                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(48.dp)
                            .background(
                                brush = Brush.horizontalGradient(listOf(Color.Green, Color.Blue)),
                                shape = RoundedCornerShape(50.dp)
                            ), contentAlignment = Alignment.Center

                    )
                    {
                        Text(
                            text = "Login",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                AnimatedVisibility(visible = loading) {
                    CircularProgressIndicator()
                }
                DividerTextComponent()

//                    LoginOrRegister(value = "Register", start = "Don't")
                ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
                    navController.navigate("SignUp") {
                        popUpTo("Login") {
                            inclusive = true
                        }
                    }
                })
            }

        }
    }
}




//@Preview
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen()
//}