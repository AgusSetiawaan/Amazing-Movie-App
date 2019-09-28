package com.agus.project.movielistapp.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

import java.util.UUID

@Entity(tableName = "watchlist")
class WatchList {
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String
        private set

    @ColumnInfo(name = "id_backend")
    var id_backend: Long? = null
        private set

    @ColumnInfo(name = "title")
    var title: String? = null
        private set

    @ColumnInfo(name = "overview")
    var overview: String? = null
        private set

    @ColumnInfo(name = "poster_path")
    var posterPath: String? = null
        private set

    @ColumnInfo(name = "runtime")
    var runtime: Long? = null
        private set

    @ColumnInfo(name = "genres")
    var genres: String? = null
        private set

    @ColumnInfo(name = "is_movies")
    var isMovies: Boolean? = null
        private set

    @ColumnInfo(name = "rate")
    var rate: Double? = null
        private set

    @Ignore
    constructor(
        id_backend: Long?,
        title: String?,
        overview: String?,
        posterPath: String?,
        runtime: Long?,
        genres: String,
        isMovies: Boolean,
        rate: Double?
    ) {
        id = UUID.randomUUID().toString()
        this.id_backend = id_backend
        this.title = title
        this.overview = overview
        this.posterPath = posterPath
        this.runtime = runtime
        this.genres = genres
        this.isMovies = isMovies
        this.rate = rate
    }

    constructor(
        id: String,
        id_backend: Long?,
        title: String?,
        overview: String?,
        posterPath: String?,
        runtime: Long?,
        genres: String,
        isMovies: Boolean,
        rate: Double?
    ) {
        this.id = id
        this.id_backend = id_backend
        this.title = title
        this.overview = overview
        this.posterPath = posterPath
        this.runtime = runtime
        this.genres = genres
        this.isMovies = isMovies
        this.rate = rate
    }


}

