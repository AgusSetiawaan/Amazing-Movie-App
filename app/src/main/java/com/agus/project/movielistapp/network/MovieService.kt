package com.agus.project.movielistapp.network


import com.agus.project.movielistapp.model.MoviesDetails
import com.agus.project.movielistapp.model.MoviesResult
import com.agus.project.movielistapp.model.TvDetails
import com.agus.project.movielistapp.model.TvResult
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") key:String?,
        @Query("language") language:String?,
        @Query("page") page:Int): Observable<Response<MoviesResult?>>

    @GET("movie/{movie_id}")
    fun getDetailsMovies(
        @Path("movie_id") id:Long,
        @Query("api_key") key:String?,
        @Query("language") language:String?): Observable<Response<MoviesDetails>>

    @GET("tv/popular")
    fun getPopularTv(
        @Query("api_key") key:String?,
        @Query("language") language:String?,
        @Query("page") page:Int): Observable<Response<TvResult?>>

    @GET("tv/{tv_id}")
    fun getDetailsTv(
        @Path("tv_id") id:Long,
        @Query("api_key") key:String?,
        @Query("language") language:String?): Observable<Response<TvDetails>>
}