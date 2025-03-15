package com.vladbakharev.shutterflytmdb.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApiService {
    @GET("genre/movie/list?language=en")
    suspend fun getGenres(@Query("api_key") apiKey: String): GenreResponse

    @GET("discover/movie?language=en&sort_by=popularity.desc")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int
    ): MovieResponse

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
