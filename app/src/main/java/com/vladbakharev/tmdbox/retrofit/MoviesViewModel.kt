package com.vladbakharev.tmdbox.retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladbakharev.tmdbox.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {
    private val apiKey = BuildConfig.TMDB_API_KEY

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres: StateFlow<List<Genre>> = _genres

    private val _selectedGenre = MutableStateFlow<Int?>(null)
    val selectedGenre: StateFlow<Int?> = _selectedGenre

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private val _configuration = MutableStateFlow<Images?>(null)
    val configuration: StateFlow<Images?> = _configuration

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    init {
        fetchGenres()
        fetchConfiguration()
    }

    private fun fetchGenres() {
        viewModelScope.launch {
            try {
                val response = TMDBApi.retrofitService.getGenres(apiKey)
                _genres.value = response.genres
                if (response.genres.isNotEmpty()) {
                    setSelectedGenre(response.genres.first().id)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setSelectedGenre(genreId: Int) {
        _selectedGenre.value = genreId
        fetchMoviesForGenre(genreId)
    }

    private fun fetchMoviesForGenre(genreId: Int) {
        viewModelScope.launch {
            try {
                val response = TMDBApi.retrofitService.getMoviesByGenre(apiKey, genreId)
                _movies.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchConfiguration() {
        viewModelScope.launch {
            try {
                val response = TMDBApi.retrofitService.getConfiguration(apiKey)
                _configuration.value = response.images
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setSelectedMovie(movie: Movie) {
        _selectedMovie.value = movie
    }
}