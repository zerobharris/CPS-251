package com.example.moviesearch

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.moviesearch.api.OmdbMovieDetailResponse

@Composable
fun MovieDetailsScreen(
    imdbId: String,
    movieViewModel: MovieViewModel
) {
    var movie by remember {
        mutableStateOf<OmdbMovieDetailResponse?>(null)
    }

    val isLoading by movieViewModel.isLoading.collectAsState()
    val error by movieViewModel.error.collectAsState()

    LaunchedEffect(imdbId) {
        movie = movieViewModel.getMovieDetailsById(imdbId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        }

        if (error != null) {
            Text(
                text = error ?: "",
                color = MaterialTheme.colorScheme.error
            )
        }

        if (movie != null) {
            AsyncImage(
                model = movie?.poster,
                contentDescription = "Movie Poster",
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
            )

            Text(
                text = movie?.title ?: "Unknown Title",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            DetailRow("Year", movie?.year)
            DetailRow("Rated", movie?.rated)
            DetailRow("Director", movie?.director)
            DetailRow("Actors", movie?.actors)

            val rottenTomatoes = movie?.ratings
                ?.firstOrNull { it.source == "Rotten Tomatoes" }
                ?.value

            DetailRow("Rotten Tomatoes", rottenTomatoes)
            DetailRow("IMDb Rating", movie?.imdbRating)
            DetailRow("Box Office", movie?.boxOffice)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Plot",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = movie?.plot ?: "No plot available"
            )
        }
    }
}

@Composable
fun DetailRow(label: String, value: String?) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = value ?: "Not available",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}