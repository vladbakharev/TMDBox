package com.vladbakharev.shutterflytmdb.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vladbakharev.shutterflytmdb.MainActivity
import com.vladbakharev.shutterflytmdb.MainBottomAppBar
import com.vladbakharev.shutterflytmdb.MainTopAppBar
import com.vladbakharev.shutterflytmdb.R
import com.vladbakharev.shutterflytmdb.backgroundBrush
import com.vladbakharev.shutterflytmdb.ui.theme.ShutterflyTMDBTheme

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
            Surface(
                modifier = modifier
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.shutterfly_logo),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {
    ShutterflyTMDBTheme {
        HomeScreen(navController = NavController(MainActivity()))
    }
}