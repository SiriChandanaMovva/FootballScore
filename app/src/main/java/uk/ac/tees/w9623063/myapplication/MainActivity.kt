package uk.ac.tees.w9623063.myapplication

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.w9623063.myapplication.detail.DetailViewModel
import uk.ac.tees.w9623063.myapplication.login.LoginViewModel
import uk.ac.tees.w9623063.myapplication.presentation.main.MainViewModel
import uk.ac.tees.w9623063.myapplication.main.MainViewModelFactory
import uk.ac.tees.w9623063.myapplication.ui.LoginRoutes
import uk.ac.tees.w9623063.myapplication.ui.Navigation
import uk.ac.tees.w9623063.myapplication.ui.theme.FootballScoreTheme
import uk.ac.tees.w9623063.myapplication.utils.Screen

class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel = viewModel(modelClass = LoginViewModel::class.java)
            val detailViewModel = viewModel(modelClass = DetailViewModel::class.java)
            val mainViewModel: MainViewModel = viewModel(
                this,
                "MainViewModel",
                MainViewModelFactory(
                    LocalContext.current.applicationContext as Application
                )
            )
            mainViewModel.getAllCache()
            val list = mainViewModel.resultList.observeAsState()
            val isLoading = mainViewModel.isLoading.collectAsState()
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val isSplashScreen = rememberSaveable { mutableStateOf(true)}
            when(navBackStackEntry?.destination?.route) {
                Screen.SplashScreen.route -> isSplashScreen.value = true
                else -> isSplashScreen.value = false
            }
            FootballScoreTheme() {
                Scaffold(
                    topBar = {
                        if (!isSplashScreen.value) {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = stringResource(id = R.string.app_name),
                                        style = MaterialTheme.typography.headlineMedium
                                    )
                                },
                                actions = {   IconButton(onClick = {
                                    mainViewModel.singOutFromFirebase()
                                    navController.popBackStack()
                                    navController.navigate(LoginRoutes.SignIn.name)
                                }) {
                                    Icon(tint = Color.White,
                                        modifier = Modifier.size(32.dp),
                                        painter = painterResource(id = R.drawable.baseline_logout_24),
                                        contentDescription = "logout"
                                    )
                                } }
                            )
                        }
                    },
                    content = {
                        Modifier.padding(paddingValues = it)
                        Navigation(
                            list = list,
                            isLoading = isLoading,
                            onRefresh = { mainViewModel.insertInDBFromNetwork() },
                            navController = navController,
                            loginViewModel =  loginViewModel
                        )
                    }
                )
            }
        }
    }
}