package com.example.finalproject.Screens

import android.util.Log
import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThankYouPage(state: SurveyState,onNavigate:()->Unit){
    var snackbarHostState = remember { SnackbarHostState() }
    var scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier=Modifier.fillMaxSize()
        .background(color = Color.White))
    {it->
        Box(modifier = Modifier.padding(20.dp)){

        Column( modifier=Modifier.padding(it)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(
            text = "Thank You for submitting the survey.", textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium)

        Text(text="Click on done to be redirected.",
            textAlign = TextAlign.Center,
            fontSize = 20.sp)

        Spacer(modifier=Modifier.height(20.dp))

        Button(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("Being redirected now.")
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
                        brush = Brush.horizontalGradient(listOf(Color.Green, Color.Blue)),
                        shape = RoundedCornerShape(50.dp)
                    ), contentAlignment = Alignment.Center

            )
            {
                Text(
                    text = "Done",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

//       Text(text=state.Name)
//        Text(text=state.ageString)
//        Text(text=state.education)
//        Text(text=state.gender)
//        Text(text=state.health)
//        Text(text=state.community)
//        Text(text=state.yesOrNo)
//        Text(text=state.firstOne)
//        Text(text=state.Selectedcheckboxes.joinToString())
//        Text(text=state.secondOne)
//        Text(text=state.third)
//        Text(text=state.text)
//        Text(text=state.textOne)

    }
    }
}

//@Preview
//@Composable
//fun ThankYouPagePreview(){
//    ThankYouPage()
//}