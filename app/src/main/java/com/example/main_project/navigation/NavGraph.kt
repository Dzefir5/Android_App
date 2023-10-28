package com.example.main_project.navigation

import MainViewModel
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.main_project.screens.HomeScreen
import com.example.main_project.screens.StartScreen

@ExperimentalMaterial3Api
@Composable
fun NavigationGraph(navController: NavHostController,viewModel:MainViewModel){
    NavHost(
        navController=navController,
        startDestination = START_ROUTE
    ){
        composable(
            route= START_ROUTE,
            exitTransition ={
                fadeOut(animationSpec = tween(500))+
                slideOutVertically (animationSpec = tween(500), targetOffsetY = {it})
            }
        ) {
            StartScreen(navController,viewModel)
        }
        composable(
            route= HOME_ROUTE,
            enterTransition ={
                fadeIn(animationSpec = tween(500))+
                slideInVertically (animationSpec = tween(500), initialOffsetY = {it})
            },
            exitTransition ={
                fadeOut(animationSpec = tween(500))+
                slideOutVertically (animationSpec = tween(500), targetOffsetY = {it})
            }
        ) {
            HomeScreen(navController,viewModel)
        }
        composable(route= CHOOSE_ROUTE) {

        }
        composable(route= EDIT_ROUTE) {

        }

    }


}