package com.example.nyctimes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.nyctimes.model.NYCviewModel
import com.example.nyctimes.navigation.Navigation
import com.example.nyctimes.ui.theme.NYCTimesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
private val viewModel : NYCviewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NYCTimesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface (modifier = Modifier.padding(innerPadding)){
                        Navigation()
                    }

//               RecyclerScreen(modifier = Modifier.fillMaxSize().padding(innerPadding))
                }
            }
        }
    }
}
