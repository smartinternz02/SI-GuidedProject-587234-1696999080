package com.example.finalproject.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.finalproject.components.ButtonComponent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyPageTwo(viewmodel: SurveyViewModel,onNavigate:()->Unit) {
    var validateHealth by remember { mutableStateOf(false)}
    var validateCommunity by remember { mutableStateOf(false)}
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFfcf5e1))
    )
    {

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)) {
            item {
                Text(
                    text = "Part I: Current Health Perception", modifier = Modifier
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
                    text = "How would you rate your current state of health?",
                    modifier = Modifier,
                    textAlign = TextAlign.Left,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
                HealthStatus(viewmodel)
                if(validateHealth){
                    Text("Choose an option to proceed",color=Color.Red)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Do you believe that public health is a significant concern in your community?\n",
                    modifier = Modifier,
                    textAlign = TextAlign.Left,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Community(viewmodel)
                if(validateCommunity){
                    Text("Choose an option to proceed",color=Color.Red)
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {


                        validateHealth=!(viewmodel.surveyState.value.healthStatus.isNotEmpty())
                        validateCommunity=!(viewmodel.surveyState.value.believePublicHealthInCommunity.isNotEmpty())

                        if(!validateCommunity && !validateHealth ){

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
private fun HealthStatus(viewmodel: SurveyViewModel){
    val radioButtons = remember {
        mutableStateListOf(
            ToggalableInfo(
                isChecked = false,
                text="Excellent"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Very Good"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Good"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Fair"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Poor"
            )


        )
    }
    radioButtons.forEachIndexed{index, info->
        Row(verticalAlignment= Alignment.CenterVertically,
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
                            viewmodel.updatedHealthStatus(info.text)
                        }
                    }


                }
            )
            Text(text=info.text)

        }
    }
}

@Composable
private fun Community(viewmodel: SurveyViewModel){
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
               /* .clickable {
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
                            viewmodel.updatedCommunityValue(info.text)
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
//fun SurveyPageTwoPreview(){
//    SurveyPageTwo()
//}