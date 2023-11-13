package uk.ac.tees.w9623063.myapplication.models

import com.google.firebase.Timestamp

data class Scores(
    val userId:String = "",
    val title:String = "",
    val description:String = "",
    val timestamp:Timestamp = Timestamp.now(),
    val colorIndex:Int = 0,
    val documentId:String = "",

)
