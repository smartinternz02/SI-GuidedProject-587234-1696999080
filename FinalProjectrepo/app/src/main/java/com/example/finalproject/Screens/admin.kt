package com.example.finalproject.Screens

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDefaults.color
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalproject.components.CircularProgressIndi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.Locale

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPanel(navController: NavController,authenticationViewModel: AuthenticationViewModel ) {
    var firebaseDatabase= FirebaseDatabase.getInstance()
    val firebaseReference=firebaseDatabase.getReference("Surveydata")
    var listofdata = mutableStateListOf<SurveyState>()
    var loading by remember{ mutableStateOf(true) }
    var authState = authenticationViewModel.authenticationState.collectAsState()


//    firebaseReference.get().addOnSuccessListener {
//       for (item in it.children) {
//               listofdata.add(MaptoObject(item.getValue<Map<String,Any>>()!!))}
//        loading=false
//    }.addOnFailureListener{
//        Log.e("firebase", "Error getting data", it)
//        loading=false
//    }



    Scaffold(
        topBar = {
            TopAppBar(actions = {

                IconButton(onClick = {
                    Firebase.auth.signOut()
                    navController.navigate("Login"){
                        popUpTo("Survey"){
                            inclusive = true
                        }
                    }
                }) {
                    Icon(Icons.Rounded.Logout, contentDescription = "")

                }
            }, title = {
                Text(
                    text = "Admin Page",
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
    ) {it->
        if(authState.value.loading)
            CircularProgressIndi()
        else
            LazyColumn(modifier = Modifier.padding(it)){
                items(authState.value.surveyList){
                    it -> ListData(it)

                }
            }


    }
}

fun MaptoObject(map :Map<String,Any>): SurveyState{
    return SurveyState(
        age = map["age"].toString(),
        education = map["education"].toString(),
        accessHealthCare = map["accessHealthCare"].toString(),
        awarePublicHealthInitiatives = map["awarePublicHealthInitiatives"].toString(),
        barriersFaced = map["barriersFaced"].toString(),
        believePublicHealthInCommunity = map["believePublicHealthInCommunity"].toString(),
        crucialStepstoEnhancePH = map["crucialStepstoEnhancePH"].toString(),
        gender = map["gender"].toString(),
        healthPromoEducation = map["healthPromoEducation"].toString(),
        healthStatus = map["HealthStatus"].toString(),
        name = map["name"].toString(),
        participatePHInitiative = map["participatePHInitiative"].toString(),
        selectedcheckboxes = map["selectedcheckboxes"] as List<String>,
        technologyRoleInPH = map["technologyRoleInPH"].toString(),
        userId = map["userId"].toString()
    )
}



@Composable
fun AnimatedListContent(visible :Boolean,it: SurveyState){

    AnimatedVisibility(visible = visible) {
        Column(modifier=Modifier.padding(20.dp)) {
            Text(text = "2.Gender", fontWeight = FontWeight.SemiBold)
            Text(text = it.gender)

            Text(text = "3.Education", fontWeight = FontWeight.SemiBold)
            Text(text = it.education)

            Text(text = "4.How would you rate your current state of health?", fontWeight = FontWeight.SemiBold)
            Text(text = it.healthStatus)

            Text(text = "5. Do you believe that public health is a significant concern in your community?", fontWeight = FontWeight.SemiBold)
            Text(text = it.believePublicHealthInCommunity)

            Text(text = "6. Are you aware of any public health initiatives or programs in your community? (e.g., vaccination campaigns, smoking cessation programs)"
                , fontWeight = FontWeight.SemiBold)
            Text(text = it.awarePublicHealthInitiatives)

            Text(text = "7. If you are aware of public health initiatives, have you personally participated in any of them?", fontWeight = FontWeight.SemiBold)
            Text(text = it.participatePHInitiative)

            Text(text = "8. How important do you believe health promotion and education are in enhancing public health?", fontWeight = FontWeight.SemiBold)
            Text(text = it.healthPromoEducation)

            Text(text = "9. In your opinion, which of the following health topics should be prioritized in public health education efforts?"
                , fontWeight = FontWeight.SemiBold)
            Text(text = it.selectedcheckboxes.joinToString(","))

            Text(text = "10. How easy is it for you to access healthcare services in your community?", fontWeight = FontWeight.SemiBold)
            Text(text = it.accessHealthCare)

            Text(text = "11. Are there any specific barriers you face when accessing healthcare services? (e.g., cost, transportation, availability)"
                , fontWeight = FontWeight.SemiBold)
            Text(text = it.barriersFaced)

            Text(text = "12. What do you believe are the most critical steps or measures that can be taken to enhance public health in the future?"
                , fontWeight = FontWeight.SemiBold)
            Text(text = it.crucialStepstoEnhancePH)

            Text(text = "13. Do you think technology and digital health solutions (e.g., telemedicine, health apps) will play a significant role in improving public health?"
                , fontWeight = FontWeight.SemiBold)
            Text(text = it.technologyRoleInPH)
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListData(it: SurveyState){
    var visible by remember{ mutableStateOf(false) }
    Column {
        ListItem(

            headlineText = { Text(text = it.name  ) },
            supportingText = { Text(text = it.age ) },
            leadingContent = {
                 Box(modifier = Modifier
                     .clip(CircleShape)
                     .background(color = Color.Blue)
                     .height(40.dp)
                     .width(40.dp)) {
                     Text(text = it.name[0].toString().uppercase(Locale.getDefault()),modifier=Modifier
                         .padding(horizontal=15.dp).padding(vertical=10.dp),
                         textAlign = TextAlign.Center,
                         color=White)
                 }
            },
            trailingContent = {
                IconButton(onClick = {
                    visible = !visible
                }) {
                    Icon(
                        if(!visible) Icons.Outlined.ExpandMore else Icons.Outlined.ExpandLess,
                        contentDescription = ""

                    )
                }
            },
        )
        AnimatedListContent(visible,it )
    }
}
