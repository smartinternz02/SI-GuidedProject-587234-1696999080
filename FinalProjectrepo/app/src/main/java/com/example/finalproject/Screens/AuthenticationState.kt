package com.example.finalproject.Screens

data class AuthenticationState(
     var isAdmin: Boolean = false,
     var loading:Boolean = false,
     var surveyList:MutableList<SurveyState> =mutableListOf<SurveyState>()
)