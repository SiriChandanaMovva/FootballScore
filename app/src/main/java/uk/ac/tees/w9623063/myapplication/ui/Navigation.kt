package uk.ac.tees.w9623063.myapplication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import uk.ac.tees.w9623063.myapplication.detail.DetailScreen
import uk.ac.tees.w9623063.myapplication.detail.DetailViewModel
import uk.ac.tees.w9623063.myapplication.home.Home
import uk.ac.tees.w9623063.myapplication.home.HomeViewModel
import uk.ac.tees.w9623063.myapplication.login.LoginScreen
import uk.ac.tees.w9623063.myapplication.login.LoginViewModel
import uk.ac.tees.w9623063.myapplication.login.SignUpScreen

enum class LoginRoutes{
    SignUp,
    SignIn
}

enum class HomeRoutes{
    Home,
    Detail
}

enum class NestedRoutes{
    Main,
    Login
}

@Composable
fun Navigation(
    navController: NavController = rememberNavController(),
    loginViewModel: LoginViewModel,
    detailViewModel: DetailViewModel,
    homeViewModel: HomeViewModel
){
    NavHost(
        navController = navController as NavHostController,
        startDestination = NestedRoutes.Main.name,
    ){
        authGraph(navController, loginViewModel)
        homeGraph(navController,detailViewModel,homeViewModel)
    }
}

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
){
    navigation(
        startDestination = LoginRoutes.SignIn.name,
        route = NestedRoutes.Login.name,
    ){
        composable(route = LoginRoutes.SignIn.name){
            LoginScreen(onNavToHomePage = {
                navController.navigate(NestedRoutes.Main.name){
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

    }
}

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    detailViewModel: DetailViewModel,
    homeViewModel: HomeViewModel,
){
    navigation(
        startDestination = HomeRoutes.Home.name,
        route = NestedRoutes.Main.name,
    ){
        composable(HomeRoutes.Home.name){
            Home(
                homeViewModel = homeViewModel,
                onNoteClick = { noteId ->
                              navController.navigate(
                                  HomeRoutes.Detail.name + "?id=$noteId"
                              ){
                                  launchSingleTop = true
                              }
                },
                navToDetailPage = {
                                  navController.navigate(HomeRoutes.Detail.name)
                },
            ) {
                navController.navigate(NestedRoutes.Login.name){
                    launchSingleTop = true
                    popUpTo(0){
                        inclusive = true
                    }
                }
            }
        }

        composable(
            route = HomeRoutes.Detail.name + "?id={id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = ""
            })
        ){ entry ->
            DetailScreen(
                detailViewModel = detailViewModel,
                noteId = entry.arguments?.getString("id") as String,
            ) {
                navController.navigateUp()
            }
        }
    }
}
















