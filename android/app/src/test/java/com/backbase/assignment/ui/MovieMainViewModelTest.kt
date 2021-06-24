package com.backbase.assignment.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.backbase.assignment.repository.MovieApiRepository
import com.backbase.assignment.repository.models.GenreModel
import com.backbase.assignment.repository.models.MovieApiResponse
import com.backbase.assignment.repository.models.MovieModel
import com.nhaarman.mockitokotlin2.atLeast
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MovieMainViewModelTest {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockRepository: MovieApiRepository

    lateinit var viewModel: MovieMainViewModel

    @Mock
    private lateinit var mockPopularMovies: Observer<MovieApiResponse>

    @Mock
    private lateinit var mockNowMovies: Observer<List<String>>

    @Mock
    private lateinit var mockErrorMessage: Observer<String>

    private val mockNowMoviesResponse = listOf("image", "image1", "image2")

    private val mockResponse = mutableListOf(
        MovieModel(
            id = 1,
            title = "Cruella",
            posterPath = "image",
            overview = "overview",
            duration = 230,
            genres = listOf(GenreModel(0, "action"))
        ),
        MovieModel(
            id = 3,
            title = "Nobody",
            posterPath = "image1",
            overview = "overview1",
            duration = 200,
            genres = listOf(GenreModel(0, "action"))
        ),
        MovieModel(
            id = 6,
            title = "Avengers",
            posterPath = "image2",
            overview = "overview2",
            duration = 330,
            genres = listOf(GenreModel(0, "comic"))
        )
    )
    private val mockApiResponse1 = MovieApiResponse(1, 230, 10, mockResponse)

    private val mockApiResponse2 =
        MovieApiResponse(2, 210, 10, mutableListOf(MovieModel(id = 10, title = "Extra")))

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = MovieMainViewModel(mockRepository).apply {
            showError.observeForever(mockErrorMessage)
            popularMovies.observeForever(mockPopularMovies)
            nowMovies.observeForever(mockNowMovies)
        }
    }

    @Test
    fun `Should get the first page of popular movies when init flow`() {
        whenever(mockRepository.getPopularMovies(PAGE_INIT)).thenReturn(
            Single.just(mockApiResponse1)
        )
        whenever(mockRepository.getNowPlayingMovies()).thenReturn(
            Single.just(mockApiResponse1)
        )
        viewModel.initItemsLoad()

        verify(mockPopularMovies).onChanged(eq(mockApiResponse1))
    }

    @Test
    fun `Should get the image path of now playing movies when init flow`() {
        whenever(mockRepository.getPopularMovies(PAGE_INIT)).thenReturn(
            Single.just(mockApiResponse1)
        )
        whenever(mockRepository.getNowPlayingMovies()).thenReturn(
            Single.just(mockApiResponse1)
        )
        viewModel.initItemsLoad()

        verify(mockNowMovies).onChanged(eq(mockNowMoviesResponse))
    }

    @Test
    fun `When user scroll should obtain next page of popular movies`() {
        whenever(mockRepository.getPopularMovies(NEXT_PAGE)).thenReturn(
            Single.just(mockApiResponse2)
        )

        viewModel.getNextPagePopularMovies()

        verify(mockPopularMovies).onChanged(eq(mockApiResponse2))
    }

    @Test
    fun `Should clear data when user open detail view`() {
        viewModel.clear()
        verify(mockPopularMovies, atLeast(2)).onChanged(eq(MovieApiResponse()))
    }

    @Test
    fun `Should show error message when popular movies service response error`() {
        whenever(mockRepository.getPopularMovies(PAGE_INIT)).thenReturn(
            Single.error(
                Throwable(ERROR_POPULAR)
            )
        )
        whenever(mockRepository.getNowPlayingMovies()).thenReturn(
            Single.just(mockApiResponse1)
        )
        viewModel.initItemsLoad()
        verify(mockErrorMessage).onChanged(eq(ERROR_POPULAR))
    }

    @Test
    fun `Should show error message when play now service response error`() {
        whenever(mockRepository.getPopularMovies(PAGE_INIT)).thenReturn(Single.just(mockApiResponse1))
        whenever(mockRepository.getNowPlayingMovies()).thenReturn(
            Single.error(
                Throwable(ERROR_NOW)
            )
        )
        viewModel.initItemsLoad()
        verify(mockErrorMessage).onChanged(eq(ERROR_NOW))
    }

    @Test
    fun `Should show error messages when both services response error`() {
        whenever(mockRepository.getPopularMovies(PAGE_INIT)).thenReturn(
            Single.error(
                Throwable(ERROR_POPULAR)
            )
        )
        whenever(mockRepository.getNowPlayingMovies()).thenReturn(
            Single.error(
                Throwable(ERROR_NOW)
            )
        )
        viewModel.initItemsLoad()

        verify(mockErrorMessage).onChanged(eq(ERROR_POPULAR))
        verify(mockErrorMessage).onChanged(eq(ERROR_NOW))

    }

    companion object {
        const val PAGE_INIT = 1
        const val NEXT_PAGE = 2
        const val ERROR_POPULAR = "Error popular"
        const val ERROR_NOW = "Error now"
    }
}