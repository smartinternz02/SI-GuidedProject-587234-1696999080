package com.example.finalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.Screens.AdminPanel
import com.example.finalproject.Screens.AuthenticationViewModel
import com.example.finalproject.Screens.LoginScreen
import com.example.finalproject.Screens.SignUpScreen
import com.example.finalproject.Screens.SurveyPage
import com.example.finalproject.Screens.SurveyPageFive
import com.example.finalproject.Screens.SurveyPageFour
import com.example.finalproject.Screens.SurveyPageSix
import com.example.finalproject.Screens.SurveyPageThree
import com.example.finalproject.Screens.SurveyPageTwo
import com.example.finalproject.Screens.SurveyViewModel
import com.example.finalproject.Screens.ThankYouPage
import com.example.finalproject.Screens.forgotPassword
import com.example.finalproject.components.CircularProgressIndi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var initialpage: String
// ...
// Initialize Firebase Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            auth = Firebase.auth
            val currentUser = auth.currentUser
            if (currentUser != null) {
                initialpage = "Survey"
            } else {
                initialpage = "Login"
            }
            var navController: NavHostController = rememberNavController()
            var viewmodel = SurveyViewModel()
            var state = viewmodel.surveyState.collectAsState()
            var authViewModel = AuthenticationViewModel()
            var authState = authViewModel.authenticationState.collectAsState()

            NavHost(navController = navController, startDestination = initialpage) {

                composable("Login") {

                    LoginScreen(authViewmodel = authViewModel,
                        onNavigate = {
                            navController.navigate("Survey") {
                                popUpTo("Login") {
                                    inclusive = true
                                }
                            }
                        }, auth = auth, navController
                    )
                }
                composable("SignUp") {

                    SignUpScreen(navController = navController, auth = auth, viewmodel = viewmodel,
                        onNavigate = {
                            navController.navigate("Survey")
                            {
                                popUpTo("SignUp") {
                                    inclusive = true
                                }
                            }
                        })
                }

                composable("Survey") {
                    if (authState.value.loading) CircularProgressIndi()
                    else if (authState.value.isAdmin) AdminPanel(navController = navController,authenticationViewModel=authViewModel)
                    else if(!authState.value.isAdmin) SurveyPage(
                        viewmodel = viewmodel, authState = authState,authViewModel = authViewModel,
                        onNavigate = { navController.navigate("SurveyTwo") }, navController =navController
                    )
                }

                composable("SurveyTwo") {
                    SurveyPageTwo(viewmodel = viewmodel,
                        onNavigate = { navController.navigate("SurveyThree") })
                }
                composable("SurveyThree") {
                    SurveyPageThree(viewmodel = viewmodel,
                        onNavigate = { navController.navigate("SurveyFour") })
                }
                composable("SurveyFour") {
                    SurveyPageFour(viewmodel = viewmodel,
                        onNavigate = { navController.navigate("SurveyFive") })
                }
                composable("SurveyFive") {
                    SurveyPageFive(viewmodel = viewmodel,
                        onNavigate = { navController.navigate("SurveySix") })
                }
                composable("SurveySix") {
                    SurveyPageSix(viewmodel = viewmodel,
                        onNavigate = {
//                            navController.popBackStack("Survey", inclusive = false)
                            navController.navigate("ThankYou")

                        },authViewModel=authViewModel)
                }

//                composable("AdminPage") {
//                    AdminPanel(onLogout = {
//                        Firebase.auth.signOut()
//
//                        navController.navigate("Login") {
//                            popUpTo("AdminPage") {
//                                inclusive = true
//                            }
//                        }
//                    })
//                }


                    composable("ThankYou") {
                        ThankYouPage(state.value,
                            onNavigate = {
                                navController.popBackStack("Survey", inclusive = false)

                            })
                    }

                composable("ForgotPassword"){
                    forgotPassword(authenticationViewModel=authViewModel,onPop={navController.popBackStack()})
                }

                }
            }
        }
    }


