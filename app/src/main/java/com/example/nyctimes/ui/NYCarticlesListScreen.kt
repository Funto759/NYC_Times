package com.example.nyctimes.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.nyctimes.util.Constants
import com.example.nyctimes.model.NYCviewModel
import com.example.nyctimes.R
import com.example.nyctimes.navigation.Navigation
import com.example.nyctimes.navigation.ScreenB
import com.example.nyctimes.ui.theme.NYCTimesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NYCarticlesListScreen(
    navController: NavController,
) {
    val viewModel: NYCviewModel = hiltViewModel()
    val state by viewModel.articles.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.provideArticles(Constants.API_KEY)
    }
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(Modifier.height(20.dp))
            Image(
                painter = painterResource(R.drawable.ic_newyorktimes_logo),
                contentDescription = "NYC Logo",
                Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(5.dp))
            InstructionText()
            Spacer(Modifier.height(5.dp))
            when (state) {
                is NYCviewModel.NYCviewState.Loading -> {
                    Text("Loading")
                }

                is NYCviewModel.NYCviewState.Success -> {
                    val articles = (state as NYCviewModel.NYCviewState.Success).data
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp), // Adds spacing between items
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp) // Padding for the whole list
                    ) {
                        items(articles) { article ->
                            OutlinedCard(
                                shape = RoundedCornerShape(16.dp), // Smooth rounded corners
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFBBDEFB) // Light blue color
                                ),
                                border = BorderStroke(1.dp, Color(0xFF1E88E5)), // Slightly darker blue border
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp) // Slightly taller for better readability
                                    .shadow(4.dp, RoundedCornerShape(16.dp)) // Adds shadow for elevation
                                    .clickable { navController.navigate(ScreenB(article.list_name_encoded)) },
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp), // Add inner padding for better text placement
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = article.display_name,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = Color.Black,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                is NYCviewModel.NYCviewState.Error -> {
                    Text("${(state as NYCviewModel.NYCviewState.Error).message}")
                }

                else -> {}
            }
        }


    }




    @Composable
    fun searchBar(
        modifier: Modifier = Modifier,
        hint: String = "",
        onSearch: (String) -> Unit = {}
    ) {

        var hintText by remember { mutableStateOf("") }

        var isHintDisplayed by remember { mutableStateOf(hint != "") }

        Box(Modifier) {
            BasicTextField(
                value = hintText,
                onValueChange = {
                    hintText = it
                    onSearch(hintText)
                },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(Color.Black),
                modifier = Modifier.fillMaxWidth().shadow(5.dp, CircleShape).background(
                    Color.White,
                    CircleShape
                ).padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }

    }
}


@Composable
fun InstructionText() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE3F2FD) // Light background for contrast
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = "📚 Select a book category to see its top-ranking books",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color(0xFF1E88E5), // Soft blue for the text
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        )
    }
}


@Preview()
@Composable
fun Preview() {
    NYCTimesTheme {
       Navigation()
    }
}


