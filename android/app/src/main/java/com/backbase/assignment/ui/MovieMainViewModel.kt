package com.backbase.assignment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.backbase.assignment.repository.MovieApiRepository
import com.backbase.assignment.repository.models.MovieApiResponse
import com.backbase.assignment.utils.BaseViewModel
import javax.inject.Inject

class MovieMainViewModel @Inject constructor(
    private val repository: MovieApiRepository
) : BaseViewModel() {
    private val _nowMovies: MutableLiveData<List<String>> = MutableLiveData(listOf())
    private val _popularMovies: MutableLiveData<MovieApiResponse> =
        MutableLiveData(MovieApiResponse())


    val popularMovies: LiveData<MovieApiResponse> = _popularMovies

    val nowMovies: LiveData<List<String>> = _nowMovies

    var mPosition: MutableLiveData<Int> = MutableLiveData(0)

    private var apiPage = 1

    private var apiTotalPages = 1

    fun initItemsLoad() {
        getPopularMovies(apiPage)
        getNowPlayingMovies()
    }

    private fun getPopularMovies(page: Int? = 1) {
        disposable.add(
            repository.getPopularMovies(page)
                .subscribe(
                    {
                        apiPage = it.page
                        apiTotalPages = it.totalPages
                        _popularMovies.value = it
                    },
                    {
                        showError.value = it.message
                    }
                )
        )
    }

    private fun getNowPlayingMovies() {
        var list = mutableListOf<String>()
        disposable.add(
            // Have better UX without pagination but the implementation is the same that popular movies
            repository.getNowPlayingMovies()
                .subscribe(
                    {
                        it.movies.map { movie ->
                            list.add(movie.posterPath.orEmpty())
                        }
                        _nowMovies.value = list
                    },
                    {
                        showError.value = it.message
                    }
                )
        )
    }

    fun getNextPagePopularMovies() {
        if (apiPage <= apiTotalPages) {
            apiPage = apiPage.plus(1)
            getPopularMovies(apiPage)
        }
    }

    fun clear() {
        apiTotalPages = 1
        apiPage = 1
        _popularMovies.value = MovieApiResponse()
    }

    fun setPosition(position: Int) {
        mPosition.value = position
    }

    fun getPosition(): Int? = mPosition.value

}