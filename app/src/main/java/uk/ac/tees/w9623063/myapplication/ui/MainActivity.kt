package uk.ac.tees.w9623063.myapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.ac.tees.w9623063.myapplication.detail.DetailViewModel
import uk.ac.tees.w9623063.myapplication.home.HomeViewModel
import uk.ac.tees.w9623063.myapplication.login.LoginViewModel
import uk.ac.tees.w9623063.myapplication.ui.theme.FootballScoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel = viewModel(modelClass = LoginViewModel::class.java)
            val detailViewModel = viewModel(modelClass = DetailViewModel::class.java)
            val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
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

