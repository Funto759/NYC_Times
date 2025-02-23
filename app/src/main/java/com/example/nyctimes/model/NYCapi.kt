package com.example.nyctimes.model

import com.example.nyctimes.data.BookDetails
import com.example.nyctimes.data.Result
import com.example.nyctimes.util.SimpleApiResponse
import com.example.nyctimes.util.SimpleApiResponsePlus
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NYCapi {


    @GET("svc/books/v3/lists/names.json")
    suspend fun getListOfBooks(
        @Query("api-key") apiKey: String
    ): SimpleApiResponse<List<Result>>

    @GET("svc/books/v3/lists/current/{name}")
    suspend fun getListOfBooksByLabel(
        @Path("name") name:String,
        @Query("api-key") apiKey: String
    ): SimpleApiResponsePlus<BookDetails>

    @GET("/articlesearch.json")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("api-key") apiKey: String
    ) : SimpleApiResponse<List<Result>>
}