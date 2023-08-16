package com.example.maingratisan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.maingratisan.view.DetailScreen
import com.example.maingratisan.view.HomeScreen
import com.example.maingratisan.viewmodel.GameViewModel

@Composable
fun Navigation(navController: NavHostController) {
    val gameViewModel = viewModel<GameViewModel>()
    LaunchedEffect(key1 = Unit, block = {
        gameViewModel.getGameData()
    })

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController, gameViewModel = gameViewModel)
        }
        composable(route = Screen.Detail.route) {
            DetailScreen(navController = navController, gameViewModel = gameViewModel)
        }
    }
}
