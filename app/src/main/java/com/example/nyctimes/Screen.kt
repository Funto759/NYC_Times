package com.example.nyctimes

sealed class Screen(val route:String) {
    object NYC_LIST :Screen("nyc_list_screen")
    object NYC_LIST_DETAILS :Screen("nyc_details_screen")
}