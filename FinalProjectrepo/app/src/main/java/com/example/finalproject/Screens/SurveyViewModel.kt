package com.example.finalproject.Screens

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SurveyViewModel : ViewModel(){
    private val _surveyState= MutableStateFlow(SurveyState())
    val surveyState:StateFlow<SurveyState> = _surveyState.asStateFlow()

    fun update(updatedName:String){
        _surveyState.update {
                state -> state.copy(name = updatedName)
        }
    }
    fun updateuserId(updateUserId:String){
        _surveyState.update {
            state->state.copy(userId=updateUserId)
        }
    }

    fun updatedCrucialStepsToEnhancePH(updatedtext:String){
        _surveyState.update {
                state -> state.copy(crucialStepstoEnhancePH = updatedtext)
        }
    }

    fun updatedTextOne(updatedtextOne:String){
        _surveyState.update {
                state -> state.copy(barriersFaced = updatedtextOne)
        }
    }

    fun updatedAgeValue(updatedAge:String){
        _surveyState.update{
            state->state.copy(age=updatedAge)
        }
    }
    fun updatedGenderValue(updatedGen:String){
        _surveyState.update{
                state->state.copy(gender =updatedGen)
        }
    }
    fun updatedEduValue(updatedEdu:String){
        _surveyState.update{
                state->state.copy(education=updatedEdu)
        }
    }

    fun updatedHealthStatus(updatedHealth:String){
        _surveyState.update{
                state->state.copy(healthStatus=updatedHealth)
        }
    }

    fun updatedCommunityValue(updatedCommunity:String){
        _surveyState.update{
                state->state.copy(believePublicHealthInCommunity = updatedCommunity)
        }
    }

    fun updatedAwareInitiative(updatedAwareInitiatives:String){
        _surveyState.update{
                state->state.copy(awarePublicHealthInitiatives = updatedAwareInitiatives)
        }
    }

    fun updatedParticipate(updateParticipateInitiative:String){
        _surveyState.update{
                state->state.copy(participatePHInitiative = updateParticipateInitiative)
        }
    }

    fun updatedHealthPromoValue(updatedHealthPromo:String){
        _surveyState.update{
                state->state.copy(healthPromoEducation = updatedHealthPromo)
        }
    }

    fun updatedcheckboxesValue(updatedCheckboxes:List<Status>){
        var selectedEfforts= mutableListOf<String>()
        updatedCheckboxes.forEach(){
                it-> selectedEfforts.add(it.title)
        }
        _surveyState.update{
                state->state.copy(selectedcheckboxes = selectedEfforts)
        }
    }

    fun updatedAccessHealthCare(updatedAccessHealthCare:String){
        _surveyState.update{
                state->state.copy(accessHealthCare = updatedAccessHealthCare)
        }
    }

    fun updatedTechnologyRoleInPH(updatedThird:String){
        _surveyState.update{
                state->state.copy(technologyRoleInPH =updatedThird )
        }
    }



}