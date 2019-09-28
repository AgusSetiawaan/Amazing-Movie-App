package com.agus.project.movielistapp.presenter

import android.content.Context
import com.agus.project.movielistapp.model.TvDetails
import com.agus.project.movielistapp.model.TvResult
import com.agus.project.movielistapp.network.MovieDataSource
import com.agus.project.movielistapp.network.Constant
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class TvPresenter(val view: View, val context: Context) {
    var composite : CompositeDisposable? = null
    var compositeDisposable = CompositeDisposable()

    init {
        composite = CompositeDisposable()
    }

    interface View{
        fun onErrorGetMovies(e:Throwable)
        fun onErrorConnection()
        fun onResponseNotOk(message:String?)
        fun onSuccessGetTvs(tvResult: TvResult?, page:Int)
        fun onSuccessGetTvDetails(tvDetails: TvDetails)
    }

    fun getPopularTvs(page:Int){

        val dataSource = MovieDataSource()

        if(dataSource.isInternetOn(context))
            dataSource.getService()
                ?.getPopularTv(Constant.AUTH, Constant.LANGUANGE,page)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<Response<TvResult?>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                        composite!!.add(d)
                    }

                    override fun onNext(response: Response<TvResult?>) {
                        if (response.code()==200)
                            view.onSuccessGetTvs(response.body(),page)
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

    fun getTvDetails(id:Long){
        val dataSource = MovieDataSource()
        if(dataSource.isInternetOn(context)) {
            dataSource.getService()
                ?.getDetailsTv(id, Constant.AUTH, Constant.LANGUANGE)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<Response<TvDetails>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                        composite!!.add(d)
                    }

                    override fun onNext(response: Response<TvDetails>) {
                        if (response.code()==200)
                            view.onSuccessGetTvDetails(response.body()!!)
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