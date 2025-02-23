package com.example.nyctimes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.nyctimes.ui.NYCarticlesDetailsScreen
import com.example.nyctimes.ui.NYCarticlesListScreen
import kotlinx.serialization.Serializable

@Composable
fun Navigation(){

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenA){
        composable<ScreenA>{
            NYCarticlesListScreen(navController = navController)
        }
        composable<ScreenB>{
            val args = it.toRoute<ScreenB>()
            NYCarticlesDetailsScreen(navController = navController, name = args.label)
        }
    }
}

@Serializable
object ScreenA

@Serializable
data class ScreenB(
    val label : String
)