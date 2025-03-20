package com.vladbakharev.tmdbox.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.vladbakharev.tmdbox.MainActivity
import com.vladbakharev.tmdbox.MainBottomAppBar
import com.vladbakharev.tmdbox.MainTopAppBar
import com.vladbakharev.tmdbox.R
import com.vladbakharev.tmdbox.TrendingMoviesGrid
import com.vladbakharev.tmdbox.TrendingTab
import com.vladbakharev.tmdbox.backgroundBrush
import com.vladbakharev.tmdbox.retrofit.MoviesViewModel
import com.vladbakharev.tmdbox.ui.theme.TMDBoxTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MoviesViewModel = viewModel()
) {
    val moviesByDay by viewModel.moviesByDay.collectAsStateWithLifecycle()
    val moviesByWeek by viewModel.moviesByWeek.collectAsStateWithLifecycle()
    val configuration by viewModel.configuration.collectAsStateWithLifecycle()

    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier,
        topBar = { MainTopAppBar(topAppBarTitle = stringResource(R.string.home)) },
        bottomBar = { MainBottomAppBar(navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(brush = backgroundBrush)
        ) {
            TrendingTab(
                selectedIndex = selectedIndex,
                onTabSelected = { selectedIndex = it }
            )
            TrendingMoviesGrid(
                movies = if (selectedIndex == 0) moviesByDay else moviesByWeek,
                configuration = configuration,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}