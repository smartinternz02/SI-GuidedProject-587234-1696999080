package com.example.finalproject.Screens


import android.widget.CheckBox
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalproject.components.ButtonComponent

@Composable
fun SurveyPageFour(viewmodel: SurveyViewModel,onNavigate:()->Unit) {
    var validateHealthPromo by remember { mutableStateOf(false)}
    var validateCheckbox by remember { mutableStateOf(false)}
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFfcf5e1))
    )
    {
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ){

            item{

                Text(
                    text = "Part III: Health Promotion and Education", modifier = Modifier
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
                    text = "How important do you believe health promotion and education are in enhancing public health?\n",
                    modifier = Modifier,
                    textAlign = TextAlign.Left,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
                HealthPromo(viewmodel)
                if(validateHealthPromo){
                    Text("Choose an option to proceed",color=Color.Red)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "In your opinion, which of the following health topics " +
                            "should be prioritized in public health education efforts? ",
                    modifier = Modifier,
                    textAlign = TextAlign.Left,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
                Checkboxes(viewmodel)
                if(validateCheckbox){
                    Text("Choose an option to proceed",color=Color.Red)
                }


                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        validateHealthPromo=!(viewmodel.surveyState.value.healthPromoEducation.isNotEmpty())
                        validateCheckbox=!(viewmodel.surveyState.value.selectedcheckboxes.isNotEmpty())
                        if(!validateHealthPromo && !validateCheckbox ){

                            onNavigate()
                        }



                    },
                    modifier = Modifier,
                    colors= ButtonDefaults.buttonColors(Color.Transparent)
                ) {
                    Box(modifier= Modifier
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
private fun HealthPromo(viewmodel: SurveyViewModel){
    val radioButtons = remember {
        mutableStateListOf(
            ToggalableInfo(
                isChecked = false,
                text="Very Important"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Somewhat Important"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Neutral"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Not Very Important"
            ),
            ToggalableInfo(
                isChecked = false,
                text="Not Important at all"
            ),
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
                            viewmodel.updatedHealthPromoValue(info.text)
                        }
                    }
                }
            )
            Text(text=info.text)

        }
    }
}

data class Status(var isChecked:Boolean,var title:String)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
private fun Checkboxes(viewmodel: SurveyViewModel) {
    var checkedState = remember {
        mutableStateListOf(
            Status(isChecked = false, title = "Nutrition and healthy eating"),
            Status(isChecked = false, title = "Physical activity and exercise"),
            Status(isChecked = false, title = "Mental health and well-being"),
            Status(isChecked = false, title = "Smoking and substance abuse prevention"),
            Status(isChecked = false, title = "Infectious disease prevention"),
            Status(isChecked = false, title = "Environmental health and sustainability"),
            Status(isChecked = false, title = "Sexual and reproductive health"),
        )
    }
    var selectedList: List<Status> = remember {
        mutableStateListOf();
    }

    checkedState.forEachIndexed { index, info ->
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = info.isChecked,
                onCheckedChange = { its ->
                    checkedState[index] = Status(isChecked = its, title = checkedState[index].title)
                    selectedList = checkedState.filter { it.isChecked }
                    viewmodel.updatedcheckboxesValue(updatedCheckboxes = selectedList)
                }
            )
            Text(text = checkedState[index].title)
        }

    }
}


//@Composable
//private fun Checkboxes(viewmodel: SurveyViewModel){
//    var checkboxes= remember{
//        mutableStateListOf(
//            ToggalableInfo(
//                isChecked = false,
//                text="Nutrition and healthy eating\n"
//            ),
//            ToggalableInfo(
//                isChecked = false,
//                text="Physical activity and exercise\n"
//            ),
//            ToggalableInfo(
//                isChecked = false,
//                text="Mental health and well-being\n"
//            ),
//            ToggalableInfo(
//                isChecked = false,
//                text="Smoking and substance abuse prevention\n"
//            ),
//            ToggalableInfo(
//                isChecked = false,
//                text="Infectious disease prevention\n"
//            ),
//            ToggalableInfo(
//                isChecked = false,
//                text="Environmental health and sustainability\n"
//            ),
//            ToggalableInfo(
//                isChecked = false,
//                text=" Sexual and reproductive health\n"
//            )
//        )
//    }
//
//    checkboxes.forEachIndexed{index,info->
//        Row(
//            verticalAlignment = Alignment.CenterVertically){
//            Checkbox(checked=info.isChecked,
//                onCheckedChange ={
//                    info.isChecked=it
////                        isChecked->
////                    checkboxes[index]=info.copy(
////                    isChecked=isChecked)
////                    for(item in checkboxes){
////                        if(item.isChecked){
////                            viewmodel.updatedcheckboxesValue(info.text)
////                        }
////                    }
//
//                    }
//                    )
//                }
//
//            Text(text=info.text)
//        }
//    }




//@Preview
//@Composable
//fun SurveyPageFourPreview(){
//    SurveyPageFour()
//}