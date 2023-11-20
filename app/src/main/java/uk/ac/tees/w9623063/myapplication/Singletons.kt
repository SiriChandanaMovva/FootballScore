package uk.ac.tees.w9623063.myapplication

import uk.ac.tees.w9623063.myapplication.data.network.retrofit.RetrofitRepository
import uk.ac.tees.w9623063.myapplication.domain.network.retrofit.RetrofitRepositoryImpl

object Singletons {

    val retrofitRepository: RetrofitRepository by lazy {
        RetrofitRepositoryImpl()
    }

}