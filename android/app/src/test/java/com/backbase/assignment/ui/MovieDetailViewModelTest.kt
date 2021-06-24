package com.backbase.assignment.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.backbase.assignment.repository.MovieApiRepository
import com.backbase.assignment.repository.models.GenreModel
import com.backbase.assignment.repository.models.MovieModel
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MovieDetailViewModelTest {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockRepository: MovieApiRepository

    lateinit var viewModel: MovieDetailViewModel

    @Mock
    private lateinit var mockDetail: Observer<MovieModel>

    @Mock
    private lateinit var mockErrorMessage: Observer<String>

    private val mockResponse = MovieModel(
        id = 1,
        title = "Cruella",
        posterPath = "path",
        overview = "overview",
        duration = 230,
        genres = listOf(GenreModel(0, "action"))
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = MovieDetailViewModel(mockRepository).apply {
            showError.observeForever(mockErrorMessage)
            detailMovie.observeForever(mockDetail)
        }
    }

    @Test
    fun `Should get the movie details when service response success`() {
        whenever(mockRepository.getMovieDetail(MOCK_MOVIE_ID)).thenReturn(Single.just(mockResponse))

        viewModel.getDetailMovie(MOCK_MOVIE_ID)

        Mockito.verify(mockDetail).onChanged(eq(mockResponse))
    }

    @Test
    fun `Should show error message when service response error`() {
        whenever(mockRepository.getMovieDetail(MOCK_MOVIE_ID)).thenReturn(
            Single.error(
                Throwable(
                    ERROR
                )
            )
        )
        viewModel.getDetailMovie(MOCK_MOVIE_ID)
        verify(mockErrorMessage).onChanged(eq(ERROR))
    }

    @Test
    fun `When runtime parameter is null should return just releaseDate - `() {
        Assert.assertEquals(viewModel.formatMovieInfo("2/09/2021", null), "2/09/2021 - ")
    }

    @Test
    fun `When releaseDate parameter is null should return just  - duration`() {
        Assert.assertEquals(viewModel.formatMovieInfo(null, 150), " - 2 h 30 m")
    }

    @Test
    fun `When don't get runtime and releaseDate should return -`() {
        Assert.assertEquals(viewModel.formatMovieInfo(null, null), " - ")
    }

    companion object {
        const val MOCK_MOVIE_ID = 1
        const val ERROR = "An error"
    }

}
