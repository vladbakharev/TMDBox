package com.vladbakharev.tmdbox

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.vladbakharev.tmdbox.navigation.NavBar
import com.vladbakharev.tmdbox.navigation.Screen
import com.vladbakharev.tmdbox.retrofit.Genre
import com.vladbakharev.tmdbox.retrofit.Images
import com.vladbakharev.tmdbox.retrofit.Movie
import com.vladbakharev.tmdbox.retrofit.MoviesViewModel
import com.vladbakharev.tmdbox.ui.theme.DarkBlue
import com.vladbakharev.tmdbox.ui.theme.LightBlue
import com.vladbakharev.tmdbox.ui.theme.LightGreen
import com.vladbakharev.tmdbox.ui.theme.TMDBoxTheme
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

//Brushes
val brush = Brush.horizontalGradient(listOf(LightGreen, LightBlue))
val backgroundBrush = Brush.linearGradient(listOf(LightBlue, DarkBlue))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    modifier: Modifier = Modifier,
    topAppBarTitle: String
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                modifier = modifier,
                text = topAppBarTitle,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    brush = brush
                )
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun MainBottomAppBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val currentRoute = navController.currentBackStackEntryFlow.map { backStackEntry ->
        backStackEntry.destination.route
    }.collectAsState(initial = Screen.Home.route)

    val items = listOf(
        NavBar.Home,
        NavBar.Genres
    )

    var selectedItem by remember { mutableIntStateOf(0) }

    items.forEachIndexed { index, navigationItem ->
        if (navigationItem.route.route == currentRoute.value) {
            selectedItem = index
        }
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        items.forEachIndexed { index, item ->

            val scale by animateFloatAsState(
                targetValue = if (selectedItem == index) 1.15f else 1f,
                animationSpec = tween(durationMillis = 300)
            )

            NavigationBarItem(
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.title,
                        modifier = modifier.graphicsLayer(
                            scaleX = scale,
                            scaleY = scale
                        ),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        modifier = modifier.graphicsLayer(
                            scaleX = scale,
                            scaleY = scale
                        ),
                        style = TextStyle(
                            brush = brush
                        )
                    )
                },
                selected = selectedItem == index,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movie: Movie? = null,
    configuration: Images? = null,
    navController: NavController,
    viewModel: MoviesViewModel
) {
    val aspectRatio = 2f / 3f

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Card(
            modifier = modifier
                .aspectRatio(aspectRatio)
                .clickable(onClick = {
                    if (movie != null) {
                        viewModel.setSelectedMovie(movie)
                        navController.navigate(Screen.Movie.createRoute(movie.id))
                    }
                }),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(16.dp)
        ) {
            val imageUrl = "${configuration?.base_url}w500${movie?.poster_path}"
            AsyncImage(
                modifier = modifier
                    .fillMaxSize(),
                model = if (configuration != null && movie?.poster_path != null) imageUrl else R.drawable.no_image,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Row(
            modifier = modifier
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (movie != null) {
                Text(
                    modifier = modifier.padding(start = 8.dp, end = 8.dp),
                    text = buildAnnotatedString {
                        append(movie.title)
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
                            append(" (")
                            append(movie.release_date.take(4))
                            append(") ")
                        }
                    },
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun MoviesGrid(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    configuration: Images?,
    navController: NavController,
    viewModel: MoviesViewModel
) {
    val gridState = rememberLazyGridState()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                if (visibleItems.isNotEmpty() &&
                    visibleItems.last().index >= movies.size - 4 &&
                    !isLoading
                ) {
                    coroutineScope.launch {
                        viewModel.loadNextPage()
                    }
                }
            }
    }

    LazyVerticalGrid(
        modifier = modifier
            .fillMaxHeight(),
        state = gridState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies) { movie ->
            MovieCard(
                movie = movie,
                configuration = configuration,
                navController = navController,
                viewModel = viewModel
            )
        }
        if (isLoading) {
            item(span = { GridItemSpan(2) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun GenresTab(
    modifier: Modifier = Modifier,
    genres: List<Genre>,
    selectedGenre: Int? = null,
    onGenreSelected: (Int) -> Unit
) {

    val selectedIndex = genres.indexOfFirst { it.id == selectedGenre }.takeIf { it >= 0 } ?: 0

    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = selectedIndex,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.secondary,
        edgePadding = 0.dp,
        divider = {},
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = modifier
                    .tabIndicatorOffset(it[selectedIndex]),
                height = (4.dp),
                width = 32.dp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    ) {
        genres.forEachIndexed { index, genre ->
            Tab(
                selected = selectedIndex == index,
                onClick = { onGenreSelected(genre.id) },
                text = {
                    Text(
                        text = genre.name,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            )
        }
    }
}

@Composable
fun TrendingMoviesGrid(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    configuration: Images?,
    navController: NavController,
    viewModel: MoviesViewModel
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxHeight(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies) { movie ->
            MovieCard(
                movie = movie,
                configuration = configuration,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun TrendingTab(
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    onTabSelected: (Int) -> Unit,
) {

    val trendingTabs = listOf(R.string.today, R.string.this_week)

    TabRow(
        modifier = modifier,
        selectedTabIndex = selectedIndex,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.secondary,
        divider = {},
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = modifier
                    .tabIndicatorOffset(it[selectedIndex]),
                height = (4.dp),
                width = 32.dp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    ) {
        trendingTabs.forEachIndexed { index, tabResId ->
            Tab(
                selected = selectedIndex == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = stringResource(tabResId),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            )
        }
    }
}

//Previews
@Preview(showBackground = true)
@Composable
fun MainTopAppBarPreview() {
    TMDBoxTheme {
        MainTopAppBar(
            topAppBarTitle = stringResource(R.string.genres)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainBottomAppBarPreview() {
    TMDBoxTheme {
        MainBottomAppBar(navController = NavController(MainActivity()))
    }
}

@Preview(showBackground = true)
@Composable
fun GenresTabPreview() {
    TMDBoxTheme {
        GenresTab(
            genres = listOf(
                Genre(1, "Action"),
                Genre(2, "Drama"),
                Genre(3, "Comedy"),
                Genre(4, "Horror"),
            ),
            selectedGenre = 1,
            onGenreSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TrendingTabPreview() {
    TMDBoxTheme {
        TrendingTab(onTabSelected = {})
    }
}