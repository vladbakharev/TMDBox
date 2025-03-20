package com.vladbakharev.tmdbox.retrofit

//Genres
data class GenreResponse(val genres: List<Genre>)
data class Genre(val id: Int, val name: String)

//Movies
data class MovieResponse(val results: List<Movie>)
data class MovieByDayResponse(val results: List<Movie>)
data class MovieByWeekResponse(val results: List<Movie>)
data class Movie(
    val id: Int,
    val title: String,
    val release_date: String,
    val poster_path: String,
    val overview: String,
    val vote_average: Double
)

//Configuration
data class ConfigurationResponse(val images: Images)
data class Images(val base_url: String)