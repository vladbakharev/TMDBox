package com.vladbakharev.tmdbox.navigation

import com.vladbakharev.tmdbox.R

sealed class NavBar(val title: String, val icon: Int, val route: Screen) {
    data object Home : NavBar("Home", R.drawable.home, Screen.Home)
    data object Genres : NavBar("Genres", R.drawable.genres, Screen.Genres)
}