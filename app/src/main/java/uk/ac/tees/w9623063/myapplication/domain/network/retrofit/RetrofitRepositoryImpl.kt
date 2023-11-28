package uk.ac.tees.w9623063.myapplication.domain.network.retrofit

import uk.ac.tees.w9623063.myapplication.data.network.retrofit.RetrofitInstance
import uk.ac.tees.w9623063.myapplication.data.network.retrofit.RetrofitRepository
import uk.ac.tees.w9623063.myapplication.domain.network.model.LiveScore
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RetrofitRepositoryImpl: RetrofitRepository {

    companion object {
        val currentDate: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }
    override suspend fun getFixtures(): Response<LiveScore> {
        return RetrofitInstance.api.getFixtures(currentDate, currentDate)
    }

    override suspend fun getLiveScores(): Response<LiveScore> {
        return RetrofitInstance.api.getLiveScore()
    }
}