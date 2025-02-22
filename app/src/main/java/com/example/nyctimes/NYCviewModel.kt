package com.example.nyctimes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nyctimes.data.Result
import com.example.nyctimes.util.ResultWrapper
import com.example.nyctimes.util.safeApiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NYCviewModel @Inject constructor(
                                       private val savedStateHandle: SavedStateHandle,
    private val api:NYCapi
) : ViewModel() {

    var _articles = MutableStateFlow<NYCviewState>(NYCviewState.IDLE)
    val articles = _articles.asStateFlow()


    fun provideArticles(apiKey:String) {
        viewModelScope.launch {
            _articles.value = NYCviewState.Loading(true)
            when (val result = safeApiCall { api.getListOfBooks(apiKey) }) {
                is ResultWrapper.Success -> {
                    _articles.value = NYCviewState.Loading(false)
                    val data = result.value.results
                    if (data.isNotEmpty()) {
                        _articles.value = NYCviewState.Success(data)
                    } else {
                        _articles.value = NYCviewState.Error("No book categories found.")
                    }
                }
                is ResultWrapper.GenericError -> {
                    _articles.value = NYCviewState.Loading(true)
                    _articles.value = NYCviewState.Error(result.error)
                }

                else -> {}
            }
        }
    }

    fun provideArticlesForSearch(apiKey:String,search:String) {
        viewModelScope.launch {
            _articles.value = NYCviewState.Loading(true)

            when (val result = safeApiCall { api.searchArticles(search,apiKey) }) {
                is ResultWrapper.Success -> {
                    _articles.value = NYCviewState.Loading(false)
                    _articles.value = NYCviewState.Success(result.value.results)
                }
                is ResultWrapper.GenericError -> {
                    _articles.value = NYCviewState.Loading(true)
                    _articles.value = NYCviewState.Error(result.error)
                }

                else -> {}
            }
        }
    }







    sealed class NYCviewState(){
         object IDLE: NYCviewState()
        data class Loading(val state : Boolean) : NYCviewState()
        data class Success(val data: List<Result>) : NYCviewState()
        data class Error(val message: String) : NYCviewState()
    }

}