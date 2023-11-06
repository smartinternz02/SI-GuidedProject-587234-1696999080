package com.example.finalproject.Screens


import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import com.example.finalproject.components.SimpleTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalproject.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyPageSix(viewmodel: SurveyViewModel,onNavigate:()->Unit,authViewModel: AuthenticationViewModel) {
    var crucialStepstoEnhancePH by remember { mutableStateOf("") }
    var validateTechnologyRoleInPH by remember { mutableStateOf(false) }
    var validateEnhance by remember { mutableStateOf(false) }
    var focus = LocalFocusManager.current
    var authState = authViewModel.authenticationState.collectAsState()
    var snackbarHostState = remember { SnackbarHostState() }
    var scope = rememberCoroutineScope()


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)

    )
    { it ->
        Box(modifier = Modifier.padding(20.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(rememberScrollState())
            ) {


                Text(
                    text = "Part V: Future of Public Health", modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Green,
                                    Color.Blue
                                )
                            )
                        ),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp, fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "What do you believe are the most critical steps or " +
                            "measures that can be taken to enhance public health in the future?\n",
                    modifier = Modifier,
                    textAlign = TextAlign.Left,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                TextField(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),
                    value = crucialStepstoEnhancePH,
                    label = { Text(text = "Enter your answer here") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(id = R.color.colorPrimary),
                        focusedLabelColor = colorResource(id = R.color.colorPrimary),
                        cursorColor = colorResource(id = R.color.colorPrimary),
                        containerColor = Color(0xFFF7F8F8)

                    ),
                    keyboardOptions = KeyboardOptions(imeAction= ImeAction.Done),
                    onValueChange = { crucialStepstoEnhancePH = it }, isError = validateEnhance
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Do you think technology and digital health solutions (e.g., telemedicine, health apps)" +
                            " will play a significant role in improving public health?\n",
                    modifier = Modifier,
                    textAlign = TextAlign.Left,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                ThirdOne(viewmodel)
                if (validateTechnologyRoleInPH) {
                    Text("Choose an option to proceed", color = Color.Red)
                }


                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {

                        focus.clearFocus()

                        validateEnhance = !(crucialStepstoEnhancePH.isNotEmpty())
                        validateTechnologyRoleInPH =
                            !(viewmodel.surveyState.value.technologyRoleInPH.isNotEmpty())

                        if (!validateTechnologyRoleInPH && !validateEnhance) {
                            viewmodel.updatedCrucialStepsToEnhancePH(crucialStepstoEnhancePH)
                            viewmodel.updateuserId(Firebase.auth.currentUser!!.uid)
                            authViewModel.postSurvery(doNavigation = {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Thank you for submitting the survey.")
                                    onNavigate()
                                }
                            },viewmodel )


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
                            text = "Submit",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                AnimatedVisibility(visible = authState.value.loading) {
                    Row(verticalAlignment=Alignment.CenterVertically){
                    CircularProgressIndicator()
                }
                }
            }

        }
    }
}




@Composable
private fun ThirdOne(viewmodel: SurveyViewModel){
    val radioButtons = remember {
        mutableStateListOf(
            ToggalableInfo(
                isChecked = false,
                text="Yes"
            ),
            ToggalableInfo(
                isChecked = false,
                text="No"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Not Sure"
            )
        )
    }
    radioButtons.forEachIndexed{index, info->
        Row(verticalAlignment= Alignment.CenterVertically,
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
                            viewmodel.updatedTechnologyRoleInPH(info.text)
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
//fun SurveyPageSixPreview(){
//    SurveyPageSix()
//}