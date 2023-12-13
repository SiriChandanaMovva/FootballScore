package uk.ac.tees.w9623063.myapplication.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import uk.ac.tees.w9623063.myapplication.models.Scores
import uk.ac.tees.w9623063.myapplication.repository.Resources
import uk.ac.tees.w9623063.myapplication.repository.StorageRepository

class HomeViewModel(
    private val repository: StorageRepository = StorageRepository()
):ViewModel() {
    var homeUiState by mutableStateOf(HomeUiState())

    val user = repository.user()
    val hasUser:Boolean
        get() = repository.hasUser()
    private val userId:String
        get() = repository.getUserId()

    fun loadNotes(){

    }
    fun signOut() = repository.signOut()
}

data class HomeUiState(
    val scoresList:Resources<List<Scores>> = Resources.Loading(),
    val noteDeletedStatus:Boolean = false
)
