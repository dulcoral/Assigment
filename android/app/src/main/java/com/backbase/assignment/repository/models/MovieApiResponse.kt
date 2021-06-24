package com.backbase.assignment.repository.models

import com.google.gson.annotations.SerializedName

data class MovieApiResponse(
    @SerializedName("page")
    var page: Int = 0,
    @SerializedName("total_results")
    var totalResults: Int = 0,
    @SerializedName("total_pages")
    var totalPages: Int = 0,
    @SerializedName("results")
    var movies: MutableList<MovieModel> = mutableListOf()
)