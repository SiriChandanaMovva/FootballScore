package uk.ac.tees.w9623063.myapplication.ui

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import uk.ac.tees.w9623063.myapplication.detail.DetailViewModel
import uk.ac.tees.w9623063.myapplication.home.HomeViewModel
import uk.ac.tees.w9623063.myapplication.home.HomeViewModelFactory
import uk.ac.tees.w9623063.myapplication.login.LoginViewModel
import uk.ac.tees.w9623063.myapplication.ui.theme.FootballScoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val homeViewModel: HomeViewModel = viewModel(
                this,
                "MainViewModel",
                HomeViewModelFactory(
                    LocalContext.current.applicationContext as Application
                )
            )
            val loginViewModel = viewModel(modelClass = LoginViewModel::class.java)
            val detailViewModel = viewModel(modelClass = DetailViewModel::class.java)
            FirebaseAuth.getInstance().signOut()



            FootballScoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(
                        loginViewModel = loginViewModel,
                        detailViewModel = detailViewModel,
                        homeViewModel = homeViewModel
                    )
                }
            }
        }
    }
}

