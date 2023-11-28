package uk.ac.tees.w9623063.myapplication.domain.network.model

data class Goalscorer(
    val away_assist: String,
    val away_assist_id: String,
    val away_scorer: String,
    val away_scorer_id: String,
    val home_assist: String,
    val home_assist_id: String,
    val home_scorer: String,
    val home_scorer_id: String,
    val info: String,
    val info_time: String,
    val score: String,
    val time: String
)