package com.backbase.assignment.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.repository.models.MovieApiResponse
import com.backbase.assignment.ui.adapters.EndlessRecyclerViewScrollListener
import com.backbase.assignment.ui.adapters.ImageMoviesAdapter
import com.backbase.assignment.ui.adapters.MoviesAdapter
import com.backbase.assignment.utils.MovieListener
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MovieHomeFragment : DaggerFragment(), MoviesAdapter.MovieItemListener {

    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesRecyclerView: RecyclerView
    private lateinit var nowMoviesAdapter: ImageMoviesAdapter
    private lateinit var nowMoviesRecyclerView: RecyclerView

    var listener: MovieListener? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val mainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(MovieMainViewModel::class.java)
    }

    lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_home, container, false)
        popularMoviesRecyclerView = view.findViewById(R.id.recyclerView_popular_movies)
        nowMoviesRecyclerView = view.findViewById(R.id.recyclerView_now_playing)
        mainViewModel.initItemsLoad()
        bindViewModel()
        initRecyclers()
        return view
    }

    private fun initRecyclers() {
        nowMoviesAdapter = ImageMoviesAdapter(listOf())
        nowMoviesRecyclerView.adapter = nowMoviesAdapter
        popularMoviesAdapter = MoviesAdapter(mutableListOf(), this)
        popularMoviesRecyclerView.adapter = popularMoviesAdapter

        scrollListener = object : EndlessRecyclerViewScrollListener(
            popularMoviesRecyclerView.layoutManager as LinearLayoutManager
        ) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                mainViewModel.getNextPagePopularMovies()
            }
        }
        popularMoviesRecyclerView.addOnScrollListener(scrollListener)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? MovieListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun bindViewModel() {
        mainViewModel.showError.observe(viewLifecycleOwner, Observer(this::showErrorMessage))
        mainViewModel.nowMovies.observe(viewLifecycleOwner, Observer(this::handleNowPlayingMovies))
        mainViewModel.popularMovies.observe(
            viewLifecycleOwner,
            Observer(this::handleMostPopularMovies)
        )
    }

    private fun showErrorMessage(message: String?) {
        listener?.showErrorMessage(message)
    }

    private fun handleMostPopularMovies(response: MovieApiResponse) {
        if (response.movies.isNotEmpty()) {
            popularMoviesAdapter.addMoreItems(response.movies)
            popularMoviesRecyclerView.scrollToPosition(mainViewModel.getPosition() ?: 0)
        }
    }

    private fun handleNowPlayingMovies(moviesPoster: List<String>) {
        if (moviesPoster.isNotEmpty()) {
            nowMoviesAdapter.imageUlrs = moviesPoster
            nowMoviesAdapter.notifyDataSetChanged()
        }
    }

    override fun onItemClick(movieId: Int, position: Int) {
        mainViewModel.setPosition(position)
        findNavController(this).navigate(MovieHomeFragmentDirections.homeToDetailFragment(movieId))
        popularMoviesAdapter.items = mutableListOf()
        mainViewModel.clear()
    }

}