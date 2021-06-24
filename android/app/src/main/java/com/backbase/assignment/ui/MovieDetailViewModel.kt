package com.backbase.assignment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.backbase.assignment.repository.MovieApiRepository
import com.backbase.assignment.repository.models.MovieModel
import com.backbase.assignment.utils.BaseViewModel
import com.backbase.assignment.utils.orZero
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val repository: MovieApiRepository
) : BaseViewModel() {
    private val _detailMovie: MutableLiveData<MovieModel> = MutableLiveData(MovieModel())
    val detailMovie: LiveData<MovieModel> = _detailMovie

    fun getDetailMovie(movieId: Int) {
        disposable.add(
            repository.getMovieDetail(movieId)
                .subscribe(
                    {
                        _detailMovie.value = it
                    },
                    {
                        showError.value = it.message
                    }
                )
        )
    }

    fun formatMovieInfo(releaseDate: String?, minutes: Int? = 0): String {
        val hrs = minutes?.div(60).orZero()
        val minute = minutes?.rem(60).orZero()
        val duration = if (hrs > 0 && minute > 0) "$hrs h $minute m" else ""
        return "${releaseDate.orEmpty()} - $duration"
    }
}