package com.vladbakharev.shutterflytmdb.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.vladbakharev.shutterflytmdb.GenresTab
import com.vladbakharev.shutterflytmdb.MainBottomAppBar
import com.vladbakharev.shutterflytmdb.MainTopAppBar
import com.vladbakharev.shutterflytmdb.MoviesGrid
import com.vladbakharev.shutterflytmdb.R
import com.vladbakharev.shutterflytmdb.backgroundBrush
import com.vladbakharev.shutterflytmdb.retrofit.MoviesViewModel

@Composable
fun GenresScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MoviesViewModel = viewModel()
) {
    val genres by viewModel.genres.collectAsStateWithLifecycle()
    val selectedGenre by viewModel.selectedGenre.collectAsStateWithLifecycle()
    val movies by viewModel.movies.collectAsStateWithLifecycle()
    val configuration by viewModel.configuration.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = { MainTopAppBar(topAppBarTitle = stringResource(R.string.genres)) },
        bottomBar = { MainBottomAppBar(navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(brush = backgroundBrush)
        ) {
            GenresTab(
                genres = genres,
                selectedGenre = selectedGenre,
                onGenreSelected = { genreId ->
                    viewModel.setSelectedGenre(genreId)
                }
            )
            MoviesGrid(
                movies = movies,
                configuration = configuration,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

/*@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun GenresScreenPreview() {
    ShutterflyTMDBTheme {
        GenresScreen(navController = NavController(MainActivity()))
    }
}*/
