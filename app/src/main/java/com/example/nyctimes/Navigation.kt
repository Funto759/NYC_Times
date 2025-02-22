package com.example.nyctimes

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nyctimes.ui.NYCarticlesListScreen

@Composable
fun Navigation(){

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.NYC_LIST.route){
        composable(route = Screen.NYC_LIST.route){
            NYCarticlesListScreen(navController = navController)
        }
//        composable(route = Screen.NYC_LIST_DETAILS.route){
//
//        }
    }
}