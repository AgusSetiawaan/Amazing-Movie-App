package com.agus.project.movielistapp.presenter

import android.content.Context
import com.agus.project.movielistapp.model.MoviesDetails
import com.agus.project.movielistapp.model.MoviesResult
import com.agus.project.movielistapp.network.MovieDataSource
import com.agus.project.movielistapp.network.Constant
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MoviesPresenter(val view: View, val context:Context) {
    var composite :CompositeDisposable? = null
    var compositeDisposable = CompositeDisposable()

    init {
        composite = CompositeDisposable()
    }

    interface View{
        fun onSuccessGetMovies(moviesResult: MoviesResult?,page:Int)
        fun onSuccessGetMoviesDetails(moviesDetails: MoviesDetails)
        fun onErrorGetMovies(e:Throwable)
        fun onErrorConnection()
        fun onResponseNotOk(message:String?)
    }

    fun getPopularMovies(page:Int){

        val dataSource = MovieDataSource()

        if(dataSource.isInternetOn(context))
            dataSource.getService()
                ?.getPopularMovies(Constant.AUTH,Constant.LANGUANGE,page)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<Response<MoviesResult?>>{
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                        composite!!.add(d)
                    }

                    override fun onNext(response: Response<MoviesResult?>) {
                        if (response.code()==200)
                            view.onSuccessGetMovies(response.body(),page)
                        else if (response.code()==401)
                            view.onResponseNotOk("Bad Credentials!!!")
                    }

                    override fun onError(e: Throwable) {
                        view.onErrorGetMovies(e)
                    }

                })
        else
            view.onErrorConnection()
    }




    fun getMovieDetails(id:Long){
        val dataSource = MovieDataSource()
        if(dataSource.isInternetOn(context)) {
            dataSource.getService()
                ?.getDetailsMovies(id, Constant.AUTH, Constant.LANGUANGE)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object :Observer<Response<MoviesDetails>>{
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                        composite!!.add(d)
                    }

                    override fun onNext(response: Response<MoviesDetails>) {
                        if (response.code()==200)
                            view.onSuccessGetMoviesDetails(response.body()!!)
                        else if (response.code()==401)
                            view.onResponseNotOk("Bad Credentials!!!")
                    }

                    override fun onError(e: Throwable) {
                        view.onErrorGetMovies(e)
                    }

                })
        }
        else{
            view.onErrorConnection()
        }
    }

}