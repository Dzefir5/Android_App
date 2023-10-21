package com.example.main_project.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
@ExperimentalMaterial3Api
@Composable
fun NavigationGraph(navController: NavHostController){
    NavHost(
        navController=navController,
        startDestination = HOME_ROUTE
    ){
        composable(route= HOME_ROUTE) {

        }
        composable(route= START_ROUTE) {

        }
        composable(route= CHOOSE_ROUTE) {

        }
        composable(route= HOME_ROUTE) {

        }

    }


}