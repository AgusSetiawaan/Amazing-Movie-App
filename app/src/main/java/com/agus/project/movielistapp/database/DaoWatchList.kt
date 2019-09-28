package com.agus.project.movielistapp.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.agus.project.movielistapp.model.WatchList

@Dao
interface Daowatchlist {


    @Query("SELECT * FROM watchlist")
    fun getAllWwatchList():LiveData<List<WatchList>>

    @Query("SELECT * FROM watchlist where id_backend =:idBackend")
    fun getDetailsMovies(idBackend: Long): WatchList

    @Insert
    fun insert(vararg watchlist: WatchList)

    @Insert
    fun insertAll(watchlistList: List<WatchList>)

    @Delete
    fun delete(watchlist: WatchList)

    @Query("DELETE FROM watchlist where 1=1")
    fun deleteAll()

    @Query("DELETE FROM watchlist where id_backend =:idBackend and is_movies=:isMovies")
    fun deleteByIdBackend(idBackend: Long, isMovies:Boolean)

    @Query("SELECT COUNT(*) FROM watchlist")
    fun selectCount(): Int?

}
