package com.example.finalproject.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalproject.R

@Composable
fun NormalTextComponent(value:String) {
    Text(text=value,
        modifier= Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style= TextStyle(
            fontSize=24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        )
            ,color= Black,
        textAlign= TextAlign.Center
    )
}


@Composable
fun CircularProgressIndi(){
    Column(modifier=Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        CircularProgressIndicator()
        Text("Please wait a moment",modifier=Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            textAlign=TextAlign.Center,
            fontSize = 20.sp
        )
    }

}

@Composable
fun UnderlinedTextComponent(value:String,navController:NavController) {
    Text(text=value,
        modifier= Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp)
            .clickable{navController.navigate("ForgotPassword")},
        style= TextStyle(
            fontSize=15.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        )
        ,color= Color.Gray,
        textAlign= TextAlign.Center,
        textDecoration = TextDecoration.Underline
    )
}


@Composable
fun HeadingTextComponent(value:String) {
    Text(text=value,
        modifier= Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp),
        style= TextStyle(
            fontSize=30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        )
        ,color= Black,
        textAlign= TextAlign.Center
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTextField(labelValue:String, value:String, onValueChange:(String)->Unit){

    OutlinedTextField(
        modifier=Modifier.fillMaxWidth()
        ,shape= RoundedCornerShape(10.dp),
        value = value,
        label={Text(text=labelValue)},
        colors=TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.colorPrimary),
            focusedLabelColor = colorResource(id = R.color.colorPrimary),
            cursorColor = colorResource(id = R.color.colorPrimary),
            containerColor = Color(0xFFF7F8F8)

        ),
        keyboardOptions = KeyboardOptions.Default,
        onValueChange =onValueChange,



    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(labelValue:String,painterResource:Painter,value:String,onValueChange: (String) -> Unit){

 OutlinedTextField(
     modifier=Modifier.fillMaxWidth()
         ,shape= RoundedCornerShape(10.dp),
        value = value,
     label={Text(text=labelValue)},
        colors=TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.colorPrimary),
            focusedLabelColor = colorResource(id = R.color.colorPrimary),
            cursorColor = colorResource(id = R.color.colorPrimary),
            containerColor = Color(0xFFF7F8F8)

        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        onValueChange =onValueChange,
     leadingIcon = {
         Icon(painter= painterResource,contentDescription="")
     }
   )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumber(labelValue:String,painterResource:Painter,value:String,onValueChange: (String) -> Unit){

    OutlinedTextField(
        modifier=Modifier.fillMaxWidth()
        ,shape= RoundedCornerShape(10.dp),
        value = value,
        label={Text(text=labelValue)},
        colors=TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.colorPrimary),
            focusedLabelColor = colorResource(id = R.color.colorPrimary),
            cursorColor = colorResource(id = R.color.colorPrimary),
            containerColor = Color(0xFFF7F8F8)

        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        onValueChange =onValueChange,
        leadingIcon = {
            Icon(painter= painterResource,contentDescription="")
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(labelValue:String,painterResource:Painter,value: String,onValueChange: (String) -> Unit) {

    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),
        value = value,
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.colorPrimary),
            focusedLabelColor = colorResource(id = R.color.colorPrimary),
            cursorColor = colorResource(id = R.color.colorPrimary),
            containerColor = Color(0xFFF7F8F8)

        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),

        onValueChange = onValueChange,
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        trailingIcon = {
            val iconImage = if (passwordVisible) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            var description = if (passwordVisible) {
                stringResource(id = R.string.hide_password)

            } else {
                stringResource(id = R.string.Show_password)
            }

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = iconImage, contentDescription = description)

            }
        },
        visualTransformation = if(passwordVisible) VisualTransformation.None else
        PasswordVisualTransformation()


    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmPassword(labelValue:String,painterResource:Painter,value: String,onValueChange: (String) -> Unit){
    var confirmPasswordVisible by remember{ mutableStateOf(false) }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),
        value = value,
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.colorPrimary),
            focusedLabelColor = colorResource(id = R.color.colorPrimary),
            cursorColor = colorResource(id = R.color.colorPrimary),
            containerColor = Color(0xFFF7F8F8)

        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        trailingIcon = {
            val iconImage = if (confirmPasswordVisible) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            var description = if (confirmPasswordVisible) {
                stringResource(id = R.string.hide_password)

            } else {
                stringResource(id = R.string.Show_password)
            }

            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                Icon(imageVector = iconImage, contentDescription = description)

            }
        },
        visualTransformation = if(confirmPasswordVisible) VisualTransformation.None else
            PasswordVisualTransformation()



    )

}


@Composable
fun ButtonComponent(value:String){
    Button(
        onClick={/*TODO*/},
       modifier= Modifier
           .fillMaxWidth()
           .heightIn(48.dp),
        contentPadding= PaddingValues(),
        colors=ButtonDefaults.buttonColors(Color.Transparent)
    ){
        Box(modifier= Modifier
            .fillMaxWidth()
            .heightIn(48.dp)
            .background(
                brush = Brush.horizontalGradient(listOf(Color.Green, Color.Blue)),
                shape = RoundedCornerShape(50.dp)
            ), contentAlignment = Alignment.Center

        ){
                Text(text=value,
                    fontSize=18.sp,
                    fontWeight=FontWeight.Bold
                )
        }
    }
}

@Composable
fun DividerTextComponent(){
    Row(modifier=Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically){
        Divider(modifier= Modifier
            .fillMaxWidth()
            .weight(1f),
            color= Color.Gray,
            thickness = 1.dp
        )
        Text(text="or",modifier=Modifier.padding(8.dp),
            fontSize=18.sp,color=Black)
        Divider(modifier= Modifier
            .fillMaxWidth()
            .weight(1f),
            color= Color.Gray,
            thickness = 1.dp
        )

    }
}

//@Composable
//fun LoginOrRegister(value:String,start:String){
//    Text(text="$start have an account? $value",modifier=Modifier. fillMaxWidth()
//        .heightIn(min = 30.dp),
//        style= TextStyle(
//            fontSize=15.sp,
//            fontWeight = FontWeight.Normal,
//            fontStyle = FontStyle.Normal,
//            textAlign = TextAlign.Center))
//}

@Composable
fun ClickableLoginTextComponent(tryingToLogin: Boolean = true, onTextSelected: (String) -> Unit) {
    val initialText =
        if (tryingToLogin) "Already have an account? " else "Don’t have an account yet? "
    val loginText = if (tryingToLogin) "Login" else "Register"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color =Color.Blue)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }

    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 21.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        ),
        text = annotatedString,
        onClick = { offset ->

            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    Log.d("ClickableTextComponent", "{${span.item}}")

                    if (span.item == loginText) {
                        onTextSelected(span.item)
                    }
                }

        },
    )
}
