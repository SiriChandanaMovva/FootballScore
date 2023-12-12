package uk.ac.tees.w9623063.myapplication.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uk.ac.tees.w9623063.myapplication.presentation.main.MainViewModel

class MainViewModelFactory(
    private val application: Application,
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            application
        ) as T
    }

}