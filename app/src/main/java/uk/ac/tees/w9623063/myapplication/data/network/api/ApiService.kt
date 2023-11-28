package uk.ac.tees.w9623063.myapplication.data.network.api

import uk.ac.tees.w9623063.myapplication.API_KEY
import uk.ac.tees.w9623063.myapplication.domain.network.model.LiveScore
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface ApiService {

    @GET("?met=Livescore&APIkey=$API_KEY")
    suspend fun getLiveScore(
    ): Response<LiveScore>

    @GET("?met=Fixtures&APIkey=$API_KEY")
    suspend fun getFixtures(
        @Query("from") fromDate: String,
        @Query("to") toDate: String
    ): Response<LiveScore>
}