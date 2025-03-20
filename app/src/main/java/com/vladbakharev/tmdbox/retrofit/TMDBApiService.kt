package com.vladbakharev.tmdbox.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApiService {
    @GET("genre/movie/list?language=en-US")
    suspend fun getGenres(@Query("api_key") apiKey: String): GenreResponse

    @GET("discover/movie?language=en-US&sort_by=popularity.desc")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int
    ): MovieResponse

    @GET("trending/movie/day?language=en-US")
    suspend fun getMoviesByToday(
        @Query("api_key") apiKey: String
    ): MovieByDayResponse

    @GET("trending/movie/week?language=en-US")
    suspend fun getMoviesByWeek(
        @Query("api_key") apiKey: String
    ): MovieByWeekResponse

    @GET("configuration")
    suspend fun getConfiguration(@Query("api_key") apiKey: String): ConfigurationResponse
}

object TMDBApi {
    private const val BASE_URL =
        "https://api.themoviedb.org/3/"

    val retrofitService: TMDBApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(TMDBApiService::class.java)
    }
}