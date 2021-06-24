package com.backbase.assignment.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.utils.Constants
import com.backbase.assignment.R
import com.backbase.assignment.repository.models.MovieModel
import com.backbase.assignment.ui.adapters.GenreMoviesAdapter
import com.backbase.assignment.utils.MovieListener
import com.bumptech.glide.Glide
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MovieDetailFragment : DaggerFragment() {
    private lateinit var genreMoviesAdapter: GenreMoviesAdapter
    private lateinit var genreMoviesRecyclerView: RecyclerView
    private lateinit var overviewDescription: TextView
    private lateinit var movieTitle: TextView
    private lateinit var movieDetails: TextView
    private lateinit var moviePoster: ImageView
    private lateinit var toolbar: Toolbar

    var listener: MovieListener? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val detailViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(MovieDetailViewModel::class.java)
    }

    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        view.apply {
            genreMoviesRecyclerView = findViewById(R.id.recyclerView_genre)
            overviewDescription = findViewById(R.id.movie_overview)
            movieTitle = findViewById(R.id.movie_title)
            movieDetails = findViewById(R.id.movie_info)
            moviePoster = findViewById(R.id.movie_image)
            toolbar = findViewById(R.id.toolbar)
        }
        initListeners()
        bindViewModel()
        detailViewModel.getDetailMovie(args.movieId)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? MovieListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun initListeners() {
        genreMoviesAdapter = GenreMoviesAdapter(listOf())
        genreMoviesRecyclerView.adapter = genreMoviesAdapter

        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun bindViewModel() {
        detailViewModel.showError.observe(viewLifecycleOwner, Observer(this::showErrorMessage))
        detailViewModel.detailMovie.observe(viewLifecycleOwner, Observer(this::initUi))
    }

    private fun showErrorMessage(message: String?) {
        listener?.showErrorMessage(message)
    }

    private fun initUi(movieDetail: MovieModel) {
        movieDetail.apply {
            genreMoviesAdapter.genres = genres.orEmpty()
            genreMoviesAdapter.notifyDataSetChanged()
            movieTitle.text = title
            movieDetails.text = detailViewModel.formatMovieInfo(releaseDate, duration)
            overviewDescription.text = overview
            Glide.with(moviePoster.context)
                .load(Constants.IMAGE_BASE_URL + posterPath)
                .into(moviePoster)
        }

    }
}