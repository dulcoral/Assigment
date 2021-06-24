package com.backbase.assignment.repository

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.backbase.assignment.utils.applySchedulers
import com.backbase.assignment.repository.models.MovieApiResponse
import com.backbase.assignment.repository.models.MovieModel
import dagger.Reusable
import io.reactivex.Single
import javax.inject.Inject

@Reusable
class MovieApiRepository @Inject constructor(
    private val movieApiService: MovieApiService
) {
    private val language =
        ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0).language

    fun getNowPlayingMovies(): Single<MovieApiResponse> {
        return movieApiService.getNowPlayingMovies(language = language).applySchedulers()
    }

    fun getMovieDetail(movieId: Int): Single<MovieModel> {
        return movieApiService.getMovieDetails(movieId = movieId, language = language)
            .applySchedulers()
    }

    fun getPopularMovies(page: Int? = null): Single<MovieApiResponse> {
        return movieApiService.getPopularMovie(language = language, page = page).applySchedulers()
    }

}