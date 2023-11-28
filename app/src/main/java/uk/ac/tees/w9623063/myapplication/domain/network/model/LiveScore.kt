package uk.ac.tees.w9623063.myapplication.domain.network.model

data class LiveScore(
    val result: List<Result>,
    val success: Int
)