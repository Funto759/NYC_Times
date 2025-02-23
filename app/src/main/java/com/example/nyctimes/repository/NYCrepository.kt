//package com.example.nyctimes.repository
//
//import com.example.nyctimes.model.NYCapi
//import com.example.nyctimes.data.NYC_Articles
//import com.example.nyctimes.util.Resources
//import dagger.hilt.android.scopes.ActivityScoped
//import javax.inject.Inject
//
//@ActivityScoped
//class NYCrepository @Inject constructor(private val api: NYCapi) {
//
//
//    suspend fun getArticles(apiKey:String): Resources<Sim>{
//         val response = try {
//             api.getListOfBooks(apiKey)
//         } catch (e:Exception){
//             return Resources.Error("An unknown error occured")
//         }
//        return Resources.Success(response)
//    }
//
//
//    suspend fun searchAricles(search:String,apiKey: String):Resources<NYC_Articles>{
//        val response = try {
//            api.searchArticles(search,apiKey)
//        } catch (e:Exception){
//            return Resources.Error("An unknown error occured")
//        }
//        return Resources.Success(response)
//    }
//}