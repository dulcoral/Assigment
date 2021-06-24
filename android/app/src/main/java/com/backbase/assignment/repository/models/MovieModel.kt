package com.backbase.assignment.repository.models

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("poster_path")
    var posterPath: String? = null,
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("popularity")
    var popularity: Double = 0.0,
    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    var voteCount: Int = 0,
    @SerializedName("release_date")
    var releaseDate: String? = null,
    @SerializedName("runtime")
    var duration: Int? = 0,
    @SerializedName("genre_ids")
    var genreIds: List<Int>? = null,
    @SerializedName("genres")
    var genres: List<GenreModel>? = null
)