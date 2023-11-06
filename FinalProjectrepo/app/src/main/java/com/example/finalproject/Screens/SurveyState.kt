package com.example.finalproject.Screens

data class SurveyState(
    val name:String="",
    val age:String="",
    val gender:String="",
    val education:String="",
    val healthStatus:String="",
    val believePublicHealthInCommunity:String="",
    val awarePublicHealthInitiatives:String="",
    val participatePHInitiative:String="",
    val healthPromoEducation:String="",
    val selectedcheckboxes: List<String> = listOf(),
    val barriersFaced:String="",
    val accessHealthCare:String="",
    val crucialStepstoEnhancePH:String="",
    val technologyRoleInPH:String="",
    val userId:String=""
)
