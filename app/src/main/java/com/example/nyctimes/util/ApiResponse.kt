package com.example.nyctimes.util

data class SimpleApiResponse<D>(val status: String, val copyright: String,val num_results:Int, val results: D)
data class SimpleApiResponsePlus<D>(val status: String, val copyright: String,val num_results:Int,val last_modified:String, val results: D)