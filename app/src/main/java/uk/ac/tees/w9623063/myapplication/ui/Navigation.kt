package uk.ac.tees.w9623063.myapplication.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uk.ac.tees.w9623063.myapplication.ui.screens.MainScreen
import uk.ac.tees.w9623063.myapplication.ui.screens.SplashScreen
import uk.ac.tees.w9623063.myapplication.ui.screens.WebScreen
import uk.ac.tees.w9623063.myapplication.utils.Screen
import uk.ac.tees.w9623063.myapplication.domain.network.model.Result
import uk.ac.tees.w9623063.myapplication.login.LoginScreen
import uk.ac.tees.w9623063.myapplication.login.LoginViewModel
import uk.ac.tees.w9623063.myapplication.login.SignUpScreen

@Composable
fun Navigation(
    list: State<List<Result>?>,
    isLoading: State<Boolean>,
    onRefresh: () -> Unit,
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(
                navController = navController,
                isLoading
            )
        }
        composable(route = LoginRoutes.SignIn.name){
            LoginScreen(onNavToHomePage = {
                navController.navigate(Screen.MainScreen.route){
                    launchSingleTop = true
                    popUpTo(route = LoginRoutes.SignIn.name){
                        inclusive = true
                    }
                }
            },
                loginViewModel = loginViewModel
            ) {
                navController.navigate(LoginRoutes.SignUp.name){
                    launchSingleTop = true
                    popUpTo(LoginRoutes.SignUp.name){
                        inclusive = true
                    }
                }
            }
        }

        composable(route = LoginRoutes.SignUp.name){
            SignUpScreen(onNavToHomePage = {
                navController.navigate(NestedRoutes.Main.name){
                    popUpTo(LoginRoutes.SignUp.name){
                        inclusive = true
                    }
                }
            },
                loginViewModel = loginViewModel
            ) {
                navController.navigate(LoginRoutes.SignIn.name)
            }
        }
        composable(
            route = Screen.MainScreen.route
        ) {
            MainScreen(
                navController = navController,
                list = list,
                isLoading = isLoading,
                onRefresh = onRefresh
            )
        }
        composable(
            route = Screen.WebScreen.route + "/{web_url}"
        ) {
            WebScreen(it.arguments?.getString("web_url")!!)
        }
    }
}