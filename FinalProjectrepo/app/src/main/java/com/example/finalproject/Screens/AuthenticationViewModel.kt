package com.example.finalproject.Screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthenticationViewModel : ViewModel(){
    private val _authenticationState= MutableStateFlow(AuthenticationState())
    val authenticationState: StateFlow<AuthenticationState> = _authenticationState.asStateFlow()

    init {
      CheckIsAdmin()
        getSurvey()
    }

    fun CheckIsAdmin(){
        _authenticationState.update {
                state -> state.copy(loading = true)
        }
        if(Firebase.auth.currentUser != null){
            var firebaseDatabase= FirebaseDatabase.getInstance()
            val firebaseReference=firebaseDatabase.getReference("users")
                .child("${Firebase.auth.currentUser!!.uid}")
                .child("admin")
            firebaseReference.get().addOnSuccessListener {
                if(it.value as Boolean) { getSurvey() }
                    _authenticationState.update { state ->
                        state.copy(isAdmin = (it.value as Boolean)!!, loading = false)
                    }

            }
        }
        else{
            _authenticationState.update {
                    state -> state.copy(loading = false)
            }
        }

    }
    fun resetPassword(email:String,onSuccess:()->Unit){

        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                        onSuccess()
                }
            }
    }



    fun postSurvery(doNavigation: ()->Unit,surveyViewModel: SurveyViewModel){
        _authenticationState.update {
                state -> state.copy(loading = true)
        }
        val database = Firebase.database
        database.getReference("Surveydata").push()
            .setValue(surveyViewModel.surveyState.value).addOnSuccessListener {
                Log.i("firebase", "Got value")
                _authenticationState.update {
                        state -> state.copy(loading = false)
                }
                doNavigation()
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
                _authenticationState.update {
                        state -> state.copy(loading = false)
                }
            }
    }

    fun getSurvey(){

        var firebaseDatabase= FirebaseDatabase.getInstance()
        val firebaseReference=firebaseDatabase.getReference("Surveydata")
        var surveyLists:MutableList<SurveyState> =mutableListOf<SurveyState>()
        firebaseReference.get().addOnSuccessListener {
            for (item in it.children) {

                surveyLists.add(MaptoObject(item.getValue<Map<String,Any>>()!!))}
            _authenticationState.update{state->state.copy(surveyList=surveyLists)}
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)

        }
    }

}