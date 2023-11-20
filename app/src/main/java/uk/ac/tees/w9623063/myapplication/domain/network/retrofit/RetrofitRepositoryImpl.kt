package uk.ac.tees.w9623063.myapplication.domain.network.retrofit

import uk.ac.tees.w9623063.myapplication.data.network.retrofit.RetrofitInstance
import uk.ac.tees.w9623063.myapplication.data.network.retrofit.RetrofitRepository
import uk.ac.tees.w9623063.myapplication.domain.network.model.LiveScore
import retrofit2.Response

class RetrofitRepositoryImpl: RetrofitRepository {

    override suspend fun getLiveScore(): Response<LiveScore> {
        return RetrofitInstance.api.getLiveScore()
    }

}