package com.example.finalproject.Screens
//For login page
data class LoginDc(
var email:String,
    var password:String,

)

//for sign in page

data class Register(
    var firstname:String,
    var lastname:String,
    var email:String,
    var password: String,
    var phoneNumber:String,
    var userId:String,
    var isAdmin:Boolean=false
)


