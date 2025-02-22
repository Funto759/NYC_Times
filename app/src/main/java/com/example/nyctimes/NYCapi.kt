package com.example.nyctimes

import com.example.nyctimes.data.Result
import com.example.nyctimes.util.SimpleApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NYCapi {


    @GET("svc/books/v3/lists/names.json")
    suspend fun getListOfBooks(
        @Query("api-key") apiKey: String
    ): SimpleApiResponse<List<Result>>

    @GET("/articlesearch.json")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("api-key") apiKey: String
    ) : SimpleApiResponse<List<Result>>
}