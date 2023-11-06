package com.example.finalproject.Screens

import com.example.finalproject.components.SimpleTextField
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyPageFive(viewmodel: SurveyViewModel,onNavigate:()->Unit) {
    var barriersFaced by remember{ mutableStateOf("") }
    var validateAccessHealthCare by remember{ mutableStateOf(false) }
    var validatebarriersFaced by remember{ mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFfcf5e1))
    )
    {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
            
        ){



                Text(
                    text = "Part IV: Access to Healthcare", modifier = Modifier
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
                    text = "How easy is it for you to access healthcare services in your community?\n",
                    modifier = Modifier,
                    textAlign = TextAlign.Left,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                AccessHealthCare(viewmodel)
                if(validateAccessHealthCare){
                    Text("Choose an option to proceed",color=Color.Red)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Are there any specific barriers" +
                            " you face when accessing healthcare services? (e.g., cost, transportation, availability)\n",
                    modifier = Modifier,
                    textAlign = TextAlign.Left,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
                TextField(modifier=Modifier.fillMaxWidth()
                    ,shape= RoundedCornerShape(10.dp),
                    value = barriersFaced,
                    label={Text(text="Enter your answer here")},
                    colors= TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(id = R.color.colorPrimary),
                        focusedLabelColor = colorResource(id = R.color.colorPrimary),
                        cursorColor = colorResource(id = R.color.colorPrimary),
                        containerColor = Color(0xFFF7F8F8)

                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    onValueChange ={barriersFaced=it}, isError = validatebarriersFaced )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        validateAccessHealthCare=!(viewmodel.surveyState.value.accessHealthCare.isNotEmpty())
                        validatebarriersFaced= !(barriersFaced.isNotEmpty())
                        if(!validateAccessHealthCare && !validatebarriersFaced){
                            viewmodel.updatedTextOne(barriersFaced)

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




@Composable
private fun AccessHealthCare(viewmodel: SurveyViewModel){
    val radioButtons = remember {
        mutableStateListOf(
            ToggalableInfo(
                isChecked = false,
                text="Very Easy"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Somewhat Easy"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Neutral"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Somewhat difficult"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Very difficult"
            ),
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
                            viewmodel.updatedAccessHealthCare(info.text)
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
//fun SurveyPageFivePreview(){
//    SurveyPageFive()
//}