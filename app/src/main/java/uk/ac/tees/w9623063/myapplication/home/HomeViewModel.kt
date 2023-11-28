package uk.ac.tees.w9623063.myapplication.home

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import uk.ac.tees.w9623063.myapplication.Singletons
import uk.ac.tees.w9623063.myapplication.data.room.MainDatabase
import uk.ac.tees.w9623063.myapplication.domain.network.model.Result
import uk.ac.tees.w9623063.myapplication.domain.room.model.ResultEntity
import uk.ac.tees.w9623063.myapplication.domain.room.repository.LiveScoreRepository
import uk.ac.tees.w9623063.myapplication.utils.mapToResult
import uk.ac.tees.w9623063.myapplication.utils.mapToResultEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.w9623063.myapplication.models.Scores
import uk.ac.tees.w9623063.myapplication.repository.Resources
import uk.ac.tees.w9623063.myapplication.repository.StorageRepository

class HomeViewModel(
    application: Application,
    private val repository: StorageRepository = StorageRepository()
) : ViewModel() {
    var homeUiState by mutableStateOf(HomeUiState())
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()
    private val networkRepository = Singletons.retrofitRepository
    private val roomRepository: LiveScoreRepository
    private var allData: List<Result>? = null
    val resultList: MutableLiveData<List<Result>> = MutableLiveData()
    val hasUser:Boolean
        get() = repository.hasUser()
    init {
        val mainDB = MainDatabase.getDB(application)
        val liveScoreDao = mainDB.liveScoreDao()
        roomRepository = LiveScoreRepository(liveScoreDao)
    }

    private suspend fun getAllAsync() =
        viewModelScope.async {
            roomRepository.getAllItem().mapToResult()
        }


    fun getAllCache() {
        try {
            viewModelScope.launch {
                allData = getAllAsync().await()
                if (allData.isNullOrEmpty()) {
                    insertInDBFromNetwork()
                } else resultList.value = allData
                _isLoading.value = false
            }
        }catch (e: Exception){
            print(e.message)
        }

    }

    fun insertItem(item: ResultEntity) {
        roomRepository.insertItem(item)
    }

    fun insertInDBFromNetwork() {
        viewModelScope.launch {
            resultList.value = getLiveScoreListAsync().await()
            resultList.value?.let { results ->
                insertAllItems(results.mapToResultEntity())
            }
            resultList.value = getAllAsync().await()
        }
    }

    private fun insertAllItems(item: List<ResultEntity>) {
        roomRepository.insertAllItems(item)
    }

    fun deleteItem(item: ResultEntity) {
        roomRepository.deleteItem(item)
    }

    private suspend fun getLiveScoreListAsync() =
        viewModelScope.async {
            networkRepository.getFixtures().body()?.result
        }

    fun signOut() {
       FirebaseAuth.getInstance().signOut()
    }


    companion object {
        private const val LIST_KEY = "listKey"
    }

}
data class HomeUiState(
    val scoresList: Resources<List<Scores>> = Resources.Loading(),
    val noteDeletedStatus:Boolean = false
)

