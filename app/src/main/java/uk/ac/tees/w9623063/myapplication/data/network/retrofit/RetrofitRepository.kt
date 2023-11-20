package uk.ac.tees.w9623063.myapplication.data.network.retrofit

import uk.ac.tees.w9623063.myapplication.domain.network.model.LiveScore
import retrofit2.Response

interface
RetrofitRepository {

    suspend fun getLiveScore(): Response<LiveScore>

}