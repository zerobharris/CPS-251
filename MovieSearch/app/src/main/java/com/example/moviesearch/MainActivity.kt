package com.example.moviesearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import retrofit2.HttpException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    val movieViewModel: MovieViewModel = viewModel(
                        factory = MovieViewModel.provideFactory(
                            MovieRepository()
                        )
                    )

                    NavHost(
                        navController = navController,
                        startDestination = "movie_search"
                    ) {

                        composable("movie_search") {
                            MovieSearchScreen(
                                navController = navController,
                                movieViewModel = movieViewModel
                            )
                        }

                        composable(
                            route = "movie_details/{imdbId}",
                            arguments = listOf(
                                navArgument("imdbId") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->

                            val imdbId = backStackEntry.arguments
                                ?.getString("imdbId") ?: ""

                            MovieDetailsScreen(
                                imdbId = imdbId,
                                movieViewModel = movieViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
