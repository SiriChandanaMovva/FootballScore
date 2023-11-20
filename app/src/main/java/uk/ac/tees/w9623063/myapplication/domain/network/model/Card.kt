package uk.ac.tees.w9623063.myapplication.domain.network.model

data class Card(
    val away_fault: String,
    val away_player_id: String,
    val card: String,
    val home_fault: String,
    val home_player_id: String,
    val info: String,
    val info_time: String,
    val time: String
)