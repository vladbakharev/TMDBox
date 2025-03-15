package com.vladbakharev.shutterflytmdb.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.vladbakharev.shutterflytmdb.R
import com.vladbakharev.shutterflytmdb.backgroundBrush
import com.vladbakharev.shutterflytmdb.retrofit.MoviesViewModel
import kotlin.math.roundToInt

@Composable
fun MovieScreen(
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel = viewModel(),
) {
    val selectedMovie by viewModel.selectedMovie.collectAsStateWithLifecycle()
    val configuration by viewModel.configuration.collectAsStateWithLifecycle()

    val aspectRatio = 2f / 3f
    val userScore = selectedMovie?.vote_average?.times(10)?.roundToInt()
    val userScoreColor = when (userScore) {
        in 70..100 -> Color.Green
        in 40..79 -> Color.Yellow
        in 1..39 -> Color.Red
        else -> Color.White
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        selectedMovie?.let {
            Box(
                modifier = modifier
                    .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 32.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                val imageUrl = "${configuration!!.base_url}w780${it.poster_path}"
                AsyncImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .aspectRatio(aspectRatio),
                    model = if (it.poster_path.isNullOrEmpty()) R.drawable.no_image else imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Surface(
                    modifier = modifier
                        .size(64.dp)
                        .offset(x = 8.dp, y = 32.dp)
                        .padding(start = 8.dp, bottom = 8.dp),
                    shape = RoundedCornerShape(64.dp),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = modifier,
                            text = "$userScore%",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = userScoreColor
                            )
                        )
                    }
                }
            }
            Text(
                modifier = modifier.padding(top = 16.dp),
                text = buildAnnotatedString {
                    append(it.title)
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
                        append(" (")
                        append(it.release_date.take(4))
                        append(") ")
                    }
                },
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                textAlign = TextAlign.Center
            )
            Text(
                modifier = modifier.padding(top = 16.dp, bottom = 32.dp),
                text = if (it.overview.isNullOrEmpty()) {
                    stringResource(id = R.string.no_overview)
                } else {
                    "\t\t\t" + it.overview
                },
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White
                ),
                textAlign = TextAlign.Justify
            )
        }
    }
}

/*
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun MovieScreenPreview() {
    ShutterflyTMDBTheme {
        MovieScreen(navController = NavController(MainActivity()))
    }
}*/
