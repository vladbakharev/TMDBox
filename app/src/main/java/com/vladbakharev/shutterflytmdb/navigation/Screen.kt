package com.vladbakharev.shutterflytmdb.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Genres : Screen("genres")
    data object Movie : Screen("movie/{movieId}") {
        fun createRoute(movieId: Int): String = "movie/$movieId"
    }
}