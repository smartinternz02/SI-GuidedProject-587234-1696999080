package com.example.finalproject.Screens

import android.content.ContentValues.TAG
import android.util.Log
import android.view.Surface
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalproject.R
import com.example.finalproject.components.ButtonComponent
import com.example.finalproject.components.ClickableLoginTextComponent
import com.example.finalproject.components.ConfirmPassword
import com.example.finalproject.components.DividerTextComponent
import com.example.finalproject.components.HeadingTextComponent
//import com.example.finalproject.components.LoginOrRegister
import com.example.finalproject.components.MyTextField
import com.example.finalproject.components.NormalTextComponent
import com.example.finalproject.components.PasswordTextField
import com.example.finalproject.components.PhoneNumber
import com.example.finalproject.components.SimpleTextField
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(viewmodel: SurveyViewModel,onNavigate:()->Unit,navController: NavController,auth:FirebaseAuth) {

    var basecontext= LocalContext.current
    var firstname by remember{mutableStateOf("")}
    var lastname by remember {mutableStateOf("")}
    var email by remember {mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    var confirmPassword by remember{ mutableStateOf("") }
    var phoneNumber by remember{mutableStateOf("")}

    var validateemail by remember{ mutableStateOf(false) }
    var validatepassword by remember{ mutableStateOf(false) }
    var validatePhoneNumber by remember{ mutableStateOf(false) }
    var validateFirstName by remember{mutableStateOf(false)}
    var validateLastName by remember{ mutableStateOf(false) }
    var isNotMatching by remember {mutableStateOf (false)}

    var focus = LocalFocusManager.current
    var loading by remember { mutableStateOf(false) }

    var snackbarHostState = remember { SnackbarHostState() }
    var scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier= Modifier
            .fillMaxSize()
            .background(color = Color.White)

    ) { it ->

       Box(modifier = Modifier.padding(20.dp)) {
           Column(
               modifier = Modifier
                   .fillMaxSize()
                   .padding(it)
                   .verticalScroll(rememberScrollState())
               , horizontalAlignment = Alignment.CenterHorizontally
           ) {
               NormalTextComponent(value = "Hey there,")
               HeadingTextComponent(value = "Create an Account")
               Spacer(modifier = Modifier.height(20.dp))
               MyTextField(
                   labelValue = "First Name",
                   painterResource(R.drawable.user),
                   value = firstname,
                   onValueChange = { firstname = it })
               if(validateFirstName)
                   Text("First Name should atleast be 3 letters",color=Color.Red)
               MyTextField(
                   labelValue = "Last Name",
                   painterResource(R.drawable.user),
                   value = lastname,
                   onValueChange = { lastname = it })
               if(validateLastName)
                   Text("Last Name should atleast be 3 letters",color=Color.Red)

               MyTextField(
                   labelValue = "Email",
                   painterResource(R.drawable.mail),
                   value = email,
                   onValueChange = { email = it })
               if(validateemail)
                   Text(text="Please enter valid Email address",color=Color.Red)

               PhoneNumber(labelValue = "Phone Number",
                   painterResource (R.drawable.phone),
                   value =phoneNumber ,
                   onValueChange ={phoneNumber=it} )
               if(validatePhoneNumber)
                   Text(text="Please enter valid Phone Number",color=Color.Red)
                    Text(text="Number should start with +91")

               //password matching

               PasswordTextField(
                   labelValue = "Password",
                   painterResource(id = R.drawable.padlock),
                   value = password,
                   onValueChange = { password = it })
               if(validatepassword)
                   Text(text="Please enter valid password",color=Color.Red)
                
               ConfirmPassword(labelValue = "Confirm Password", painterResource (id=R.drawable.padlock), value =confirmPassword ,
                   onValueChange = { confirmPassword = it })
               if (isNotMatching) {
                   Text(text = "Password does not match",color=Color.Red)
               }
               
               Spacer(modifier = Modifier.height(50.dp))
               Button(
                   onClick = {/* navController.navigate("Home")*/

                       focus.clearFocus()
                       isNotMatching =password!=confirmPassword

                       validateemail =
                           !(email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z0-9.-]+\$")))
                       validatepassword =
                           !(password.matches(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")))

                       validatePhoneNumber=!(phoneNumber.matches(Regex("^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}\$")))

                       validateFirstName=!(firstname.matches(Regex("^[A-Za-z][a-zA-Z]{2,}\$")))

                       validateLastName=!(lastname.matches(Regex("^[A-Za-z][a-zA-Z]{2,}\$")))

                       if (!validateemail && !validatepassword && !isNotMatching && !validatePhoneNumber &&!validateFirstName &&!validateLastName) {
                           loading=true
                           auth.createUserWithEmailAndPassword(email,password)
                               .addOnCompleteListener() { task ->
                                   if (task.isSuccessful) {
                                       val user = Register(
                                           firstname = firstname,
                                           email = email,
                                           password = password,
                                           lastname = lastname,
                                           phoneNumber=phoneNumber,
                                           userId = Firebase.auth.currentUser!!.uid
                                       )
                                       scope.launch {
                                           snackbarHostState.showSnackbar("Sign Up Successful")
                                       }
                                       val database = Firebase.database
                                       database.getReference("users").child(user.userId).setValue(user)
                                           .addOnSuccessListener {
                                               Log.i("firebase", "Got value")
                                               onNavigate()
                                           }.addOnFailureListener {
                                           Log.e("firebase", "Error getting data", it)
                                       }
                                       Log.i("firebase", "authentication ")
                                       loading = false


                                   }

                                   else {
                                       // If sign in fails, display a message to the user.
                                       Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                       Toast.makeText(
                                           basecontext,
                                           "Already Registered",
                                           Toast.LENGTH_SHORT,
                                       ).show()


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
                           text = "Register",
                           fontSize = 18.sp,
                           fontWeight = FontWeight.Bold
                       )
                   }
               }


               Spacer(modifier = Modifier.height(10.dp))
               DividerTextComponent()
//       LoginOrRegister(value = "Login",start="Already")
               ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
                   navController.navigate("Login") {
                       popUpTo("SignUp") {
                           inclusive = true
                       }
                   }

               })
               AnimatedVisibility(visible = loading) {
                   CircularProgressIndicator()
               }
           }


       }
   }


}

//@Preview
//@Composable
//fun SignUpScreenPreview(){
//    SignUpScreen()
//}