package com.agus.project.movielistapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MoviesResult(
    @SerializedName("page")
    @Expose
    val page: Int?,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int?,
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int?,
    @SerializedName("results")
    @Expose
    val results: List<MovieItem>? = null
)

data class MovieItem(
    @SerializedName("original_title")
    @Expose
    val originalTitle: String,
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("overview")
    val releaseDate: String,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String?,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double
)

data class TvResult(
    @SerializedName("page")
    @Expose
    val page: Int?,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int?,
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int?,
    @SerializedName("results")
    @Expose
    val results: List<TvItem>? = null
)

data class TvItem(
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String?
)

data class Item(
    val title: String,
    val id: Long,
    val voteAverage: Double,
    val posterPath: String?
)