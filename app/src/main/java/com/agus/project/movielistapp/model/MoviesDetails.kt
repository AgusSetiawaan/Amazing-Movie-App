package com.agus.project.movielistapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoviesDetails(
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("original_title")
    @Expose
    val original_title: String,
    @SerializedName("overview")
    @Expose
    val overview: String,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String?,
    @SerializedName("runtime")
    @Expose
    val runtime: Long,
    @SerializedName("vote_average")
    @Expose
    val vote: Double,
    @SerializedName("genres")
    @Expose
    val genres: List<Genre>
)

data class Genre(
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("name")
    @Expose
    val name: String
):Serializable

data class TvDetails(
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("name")
    @Expose
    val original_title: String,
    @SerializedName("overview")
    @Expose
    val overview: String,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String?,
    @SerializedName("runtime")
    @Expose
    val runtime: Long,
    @SerializedName("vote_average")
    @Expose
    val vote: Double,
    @SerializedName("genres")
    @Expose
    val genres: List<Genre>
)

data class Details(
    val id:Long,
    val title:String,
    val overview: String,
    val posterPath: String?,
    val runtime: Long,
    val vote: Double,
    val genres: List<Genre>,
    val isMovies: Boolean
):Serializable