package com.vladbakharev.shutterflytmdb.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vladbakharev.shutterflytmdb.retrofit.MoviesViewModel
import com.vladbakharev.shutterflytmdb.screens.GenresScreen
import com.vladbakharev.shutterflytmdb.screens.HomeScreen
import com.vladbakharev.shutterflytmdb.screens.MovieScreen


@Composable
fun NavigationStack() {
    val navController = rememberNavController()
    val moviesViewModel: MoviesViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable(
            route = Screen.Home.route,
            enterTransition = { fadeIn() },
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Genres.route,
            enterTransition = { fadeIn() },
        ) {
            GenresScreen(navController = navController, viewModel = moviesViewModel)
        }
        composable(
            route = Screen.Movie.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            MovieScreen(viewModel = moviesViewModel)
        }
    }
}