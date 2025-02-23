package com.example.nyctimes.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.room.util.TableInfo
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.nyctimes.util.Constants
import com.example.nyctimes.model.NYCviewModel
import com.example.nyctimes.R
import com.example.nyctimes.navigation.Navigation
import com.example.nyctimes.navigation.ScreenC
import com.example.nyctimes.ui.theme.NYCTimesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NYCarticlesDetailsScreen(
    navController: NavController,
    name: String
) {
    val viewModel: NYCviewModel = hiltViewModel()
    val state by viewModel.articlesDetails.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.provideArticlesDetails(name,Constants.API_KEY)
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
            when (state) {
                is NYCviewModel.NYCviewState.Loading -> {
                    Text("Loadingggg")
                }

                is NYCviewModel.NYCviewState.Details -> {
                    val details = (state as NYCviewModel.NYCviewState.Details).data
                    LazyColumn(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        items(details){
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    // Book Rank
                                    Text(
                                        text = "Rank #${it.rank}",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp
                                        ),
                                        color = Color(0xFF6200EE),
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )

                                    // Book Image
                                    Image(
                                        painter = rememberAsyncImagePainter(it.book_image),
                                        contentDescription = "Book Cover",
                                        modifier = Modifier
                                            .size(200.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color.LightGray),
                                        contentScale = ContentScale.Crop
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Title and Author
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = it.title,
                                            style = MaterialTheme.typography.titleLarge.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            textAlign = TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "by ${it.author}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Gray,
                                            textAlign = TextAlign.Center
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Book Description
                                    Text(
                                        text = it.description,
                                        style = MaterialTheme.typography.bodyMedium,
                                        maxLines = 4,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Justify,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Price Section
                                    Text(
                                        text = "Price: ${it.price}",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp
                                        ),
                                        color = Color(0xFF03DAC5)
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


