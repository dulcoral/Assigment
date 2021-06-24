package com.backbase.assignment.repository

import com.backbase.assignment.repository.models.MovieModel
import com.backbase.assignment.repository.models.MovieApiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    // 1. currently playing movies: Get a list of movies in theatres https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=undefined
    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query("language") language: String?): Single<MovieApiResponse>

    // 2. Display the most popular movies: Get a list of the current popular movies https://api.themoviedb.org/3/movie/popular?language=en-US&page=1
    @GET("movie/popular")
    fun getPopularMovie(
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Single<MovieApiResponse>

    // 3. Detailed screen: Get information about a movie https://api.themoviedb.org/3/movie/{MOVIE_ID}?language=en-US
    @GET("movie/{id}")
    fun getMovieDetails(
        @Path("id") movieId: Int,
        @Query("language") language: String?
    ): Single<MovieModel>
}