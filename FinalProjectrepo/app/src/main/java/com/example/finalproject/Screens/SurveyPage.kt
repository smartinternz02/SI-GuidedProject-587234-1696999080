package com.example.finalproject.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalproject.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun SurveyPage(
    viewmodel: SurveyViewModel,
    authViewModel: AuthenticationViewModel,
    authState: State<AuthenticationState>, onNavigate:()->Unit,
    navController:NavController,) {
    var name by remember { mutableStateOf("") }
    var validateName by remember { mutableStateOf(false) }
    var validateAge by remember { mutableStateOf(false) }
    var validateEducation by remember { mutableStateOf(false) }
    var validateGender by remember { mutableStateOf(false) }
    var focus= LocalFocusManager.current


     Scaffold(
                topBar = {
                    TopAppBar(actions = {
                        IconButton(onClick = {
                           Firebase.auth.signOut()
                            navController.navigate("Login") {
                                popUpTo("Survey") {
                                    inclusive = true
                                }
                            }
                        }) {
                            Icon(Icons.Rounded.Logout, contentDescription = "")

                        }
                    }, title = {
                        Text(
                            text = "Public Health Enhancement Survey",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Green,
                                        Color.Blue
                                    )
                                ),
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    })
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFFfcf5e1))
            ) { it ->
                Box(modifier = Modifier.padding(20.dp)) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .verticalScroll(rememberScrollState())
                    ) {


//                    Text(
//                        text = "Public Health Enhancement Survey",
//                        modifier = Modifier.fillMaxWidth(),
//                        textAlign = TextAlign.Center,
//                        style = TextStyle(
//                            brush = Brush.horizontalGradient(
//                                colors = listOf(
//                                    Color.Green,
//                                    Color.Blue
//                                )
//                            ),
//                            fontSize = 30.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                    )
                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Introduction",
                            modifier = Modifier,
                            textAlign = TextAlign.Left,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "This survey aims to understand public perspectives on efforts to enhance public health. Your responses will help us gain insights into your views on various aspects of public health improvement.\n",
                            modifier = Modifier,
                            textAlign = TextAlign.Left,
                            fontSize = 19.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))


                        ////Name Text field from App Components
//                    SimpleTextField(
//                        labelValue = "Name",
//                        value = name,
//                        onValueChange = { name = it
//                        }
//
//                        )


                        TextField(
                            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),
                            value = name,
                            label = { Text(text = "Name") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = colorResource(id = R.color.colorPrimary),
                                focusedLabelColor = colorResource(id = R.color.colorPrimary),
                                cursorColor = colorResource(id = R.color.colorPrimary),
                                containerColor = Color(0xFFF7F8F8)

                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { focus.clearFocus() }),
                            onValueChange = { name = it }, isError = validateName
                        )



                        Spacer(modifier = Modifier.height(10.dp))


                        Text(
                            text = "Demographics",
                            modifier = Modifier,
                            textAlign = TextAlign.Left,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Age",
                            modifier = Modifier,
                            textAlign = TextAlign.Left,
                            fontSize = 19.sp
                        )

                        Age(viewmodel)
                        if (validateAge) {
                            Text("Please choose an option to proceed", color = Color.Red)
                        }
                        Text(
                            text = "Gender",
                            modifier = Modifier,
                            textAlign = TextAlign.Left,
                            fontSize = 19.sp
                        )
                        Gender(viewmodel)
                        if (validateGender) {
                            Text("Please choose an option to proceed", color = Color.Red)
                        }
                        Text(
                            text = "Education",
                            modifier = Modifier,
                            textAlign = TextAlign.Left,
                            fontSize = 19.sp
                        )
                        Education(viewmodel)
                        if (validateEducation) {
                            Text("Please choose an option to proceed", color = Color.Red)
                        }


                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                validateName = !(name.isNotEmpty())
                                validateAge = !(viewmodel.surveyState.value.age.isNotEmpty())
                                validateGender = !(viewmodel.surveyState.value.gender.isNotEmpty())
                                validateEducation =
                                    !(viewmodel.surveyState.value.education.isNotEmpty())
                                if (!validateName && !validateAge && !validateGender && !validateEducation) {
                                    viewmodel.update(name)
                                    onNavigate()
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
                                        brush = Brush.horizontalGradient(
                                            listOf(
                                                Color.Green,
                                                Color.Blue
                                            )
                                        ),
                                        shape = RoundedCornerShape(50.dp)
                                    ), contentAlignment = Alignment.Center

                            )
                            {
                                Text(
                                    text = "Next Page",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

}



@Composable
private fun Age(viewmodel:SurveyViewModel){
    val radioButtons = remember {
        mutableStateListOf(
            ToggalableInfo(
                isChecked = false,
                text="Under 18"
            ),
            ToggalableInfo(
                isChecked = false,
                text="18-24"
            ),
            ToggalableInfo(
                isChecked = false,
                text="25-34"
            ),
            ToggalableInfo(
                isChecked = false,
                text="35-44"
            ),
            ToggalableInfo(
                isChecked = false,
                text="45-54"
            ),
            ToggalableInfo(
                isChecked = false,
                text="55-64"
            ),
            ToggalableInfo(
                isChecked = false,
                text="65 or older"
            )


        )
    }
    radioButtons.forEachIndexed{index, info->
        Row(verticalAlignment=Alignment.CenterVertically,
            modifier= Modifier
                .padding(start = 32.dp)
                .padding(end = 16.dp)
        )
        {
            RadioButton(selected  = info.isChecked,
                onClick = {
                    radioButtons.replaceAll {
                        it.copy(
                            isChecked = it.text == info.text
                        )
                    }
                    for(item in radioButtons){
                        if(item.isChecked){
                            viewmodel.updatedAgeValue(info.text)
                        }
                    }
                }
            )
            Text(text=info.text)

        }
    }
}

@Composable
private fun Education(viewmodel: SurveyViewModel){
    val radioButtons = remember {
        mutableStateListOf(
            ToggalableInfo(
                isChecked = false,
                text="High School or below"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Bachelor's Degree"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Master's Degree"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Ph.D. or higher\n"
            )


        )
    }
    radioButtons.forEachIndexed{index, info->
        Row(verticalAlignment=Alignment.CenterVertically,
            modifier= Modifier
                .padding(start = 32.dp)
                /*.clickable {
                    radioButtons.replaceAll {
                        it.copy(
                            isChecked = it.text == info.text
                        )
                    }
                }*/
                .padding(end = 16.dp)
        )
        {
            RadioButton(selected  = info.isChecked,
                onClick = {
                    radioButtons.replaceAll {
                        it.copy(
                            isChecked = it.text == info.text
                        )
                    }
                    for(item in radioButtons){
                        if(item.isChecked){
                            viewmodel.updatedEduValue(info.text)
                        }
                    }
                }
            )
            Text(text=info.text)

        }
    }
}

@Composable
private fun Gender(viewmodel: SurveyViewModel){
    val radioButtons = remember {
        mutableStateListOf(
            ToggalableInfo(
                isChecked = false,
                text="Male"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Female"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Non-Binary"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Prefer Not to say"
            )


        )
    }
    radioButtons.forEachIndexed{index, info->
        Row(verticalAlignment=Alignment.CenterVertically,
            modifier= Modifier
                .padding(start = 32.dp)
                .padding(end = 16.dp)
        )
        {
            RadioButton(selected  = info.isChecked,
                onClick = {
                    radioButtons.replaceAll {
                        it.copy(
                            isChecked = it.text == info.text
                        )
                    }
                    for(item in radioButtons){
                        if(item.isChecked){
                            viewmodel.updatedGenderValue(info.text)
                        }
                    }

                }
            )
            Text(text=info.text)

        }
    }
}

//@Preview
//@Composable
//fun OutlinedTextFieldWithCheckboxesPreview() {
//    SurveyPage()
//}