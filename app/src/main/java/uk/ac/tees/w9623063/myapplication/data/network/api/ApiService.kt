package uk.ac.tees.w9623063.myapplication.data.network.api

import uk.ac.tees.w9623063.myapplication.API_KEY
import uk.ac.tees.w9623063.myapplication.domain.network.model.LiveScore
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("?met=Livescore&APIkey=$API_KEY")
    suspend fun getLiveScore(): Response<LiveScore>
}