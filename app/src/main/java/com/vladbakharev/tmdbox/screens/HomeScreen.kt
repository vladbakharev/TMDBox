package com.vladbakharev.tmdbox.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.vladbakharev.tmdbox.MainActivity
import com.vladbakharev.tmdbox.MainBottomAppBar
import com.vladbakharev.tmdbox.MainTopAppBar
import com.vladbakharev.tmdbox.R
import com.vladbakharev.tmdbox.backgroundBrush
import com.vladbakharev.tmdbox.ui.theme.TMDBoxTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Scaffold(
        modifier = modifier,
        topBar = { MainTopAppBar(topAppBarTitle = stringResource(R.string.home)) },
        bottomBar = { MainBottomAppBar(navController = navController) }

    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(brush = backgroundBrush),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {
    TMDBoxTheme {
        HomeScreen(navController = NavController(MainActivity()))
    }
}