package com.example.nyctimes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.nyctimes.data.Book
import com.example.nyctimes.ui.BookDetailsScreen
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
        composable<ScreenC>{
            val args = it.toRoute<ScreenC>()
            BookDetailsScreen(navController = navController, image = args.image, author = args.author, rank = args.rank, tittle = args.tittle, description = args.description, price = args.price)
        }
    }
}

@Serializable
object ScreenA

@Serializable
data class ScreenB(
    val label : String
)

@Serializable
data class ScreenC(
    val image:String,
    val author:String,
    val rank:String,
    val tittle:String,
    val description:String,
    val price:String

)