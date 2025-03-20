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

    private val _currentPage = MutableStateFlow(1)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _moviesByDay = MutableStateFlow<List<Movie>>(emptyList())
    val moviesByDay: StateFlow<List<Movie>> = _moviesByDay

    private val _moviesByWeek = MutableStateFlow<List<Movie>>(emptyList())
    val moviesByWeek: StateFlow<List<Movie>> = _moviesByWeek

    init {
        fetchGenres()
        fetchConfiguration()
        fetchMoviesByDay()
        fetchMoviesByWeek()
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
        _movies.value = emptyList()
        _currentPage.value = 1
        fetchMoviesForGenre(genreId, 1)
    }

    private fun fetchMoviesForGenre(genreId: Int, page: Int) {
        if (_isLoading.value) return
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = TMDBApi.retrofitService.getMoviesByGenre(apiKey, genreId, page)
                _movies.value += response.results
                _currentPage.value = page
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
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

    fun loadNextPage() {
        val genreId = _selectedGenre.value ?: return
        fetchMoviesForGenre(genreId, _currentPage.value + 1)
    }

    fun setSelectedMovie(movie: Movie) {
        _selectedMovie.value = movie
    }

    private fun fetchMoviesByDay() {
        viewModelScope.launch {
            try {
                val response = TMDBApi.retrofitService.getMoviesByToday(apiKey)
                _moviesByDay.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchMoviesByWeek() {
        viewModelScope.launch {
            try {
                val response = TMDBApi.retrofitService.getMoviesByWeek(apiKey)
                _moviesByWeek.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}