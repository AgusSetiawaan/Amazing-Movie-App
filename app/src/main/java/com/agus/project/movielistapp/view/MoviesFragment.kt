package com.agus.project.movielistapp.view

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.agus.project.movielistapp.GridSpacingItemDecoration
import com.agus.project.movielistapp.R
import com.agus.project.movielistapp.model.*
import com.agus.project.movielistapp.presenter.MoviesPresenter
import kotlinx.android.synthetic.main.movies_fragment.*


class MoviesFragment: Fragment(),  MoviesPresenter.View, MainActivity.MoviesListener{

    var _recyclerView:RecyclerView? = null
    var moviesAdapter: MoviesAdapter? = null
    var presenter:MoviesPresenter? = null
    var width = 0
    var materialDialog:MaterialDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view  =inflater.inflate(R.layout.movies_fragment,container,false)
        _recyclerView= view.findViewById(R.id.recycler_view)
        moviesAdapter = MoviesAdapter(null,0,0,this,0)

        presenter = MoviesPresenter(this,context!!)

        presenter!!.getPopularMovies(1)

        callDialog()


        setRecyclerView()

        return view
    }

    private fun setRecyclerView(){
        val display = (activity!!).windowManager.defaultDisplay

        if (android.os.Build.VERSION.SDK_INT >= 13) {
            val size = Point()
            display.getSize(size)
            width = size.x
        } else {
            width = display.width
        }

        //recycler
        val gridLayoutManager = GridLayoutManager(context, 2)
        val spanCount = 2
        val spacing = 10
        val includeEdge = true

        gridLayoutManager.spanSizeLookup = object:GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                when (moviesAdapter!!.getItemViewType(position)) {
                    MoviesAdapter.TYPE_FOOTER -> return 2
                    MoviesAdapter.TYPE_ITEM -> return 1
                    else -> return 1
                }
            }

        }

        _recyclerView!!.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount,
                spacing,
                includeEdge
            )
        )
        _recyclerView!!.layoutManager = gridLayoutManager
        _recyclerView!!.setHasFixedSize(true)
        _recyclerView!!.adapter = moviesAdapter
    }

    override fun onSuccessGetMovies(moviesResult: MoviesResult?, page: Int) {
        if (moviesResult!!.results != null && moviesResult!!.results!!.isNotEmpty()){

            moviesAdapter!!.setMoviesList(copyListMovie(moviesResult.results!!),page,moviesResult.totalPages!!,width)
            _recyclerView!!.visibility = View.VISIBLE
            layout_empty.visibility = View.GONE
            moviesAdapter!!.notifyDataSetChanged()
            val layoutManager = _recyclerView!!.layoutManager as GridLayoutManager
            layoutManager.scrollToPositionWithOffset(0,0)

        }

        materialDialog!!.dismiss()
    }

    override fun onSuccessGetMoviesDetails(moviesDetails: MoviesDetails) {
        materialDialog!!.dismiss()
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra("movie",copyDetails(moviesDetails))
        startActivity(intent)
    }

    override fun onErrorGetMovies(e: Throwable) {
        Toast.makeText(context,e.localizedMessage,Toast.LENGTH_SHORT).show()
        Log.e("ERROR",e.localizedMessage)
        materialDialog!!.dismiss()
    }

    override fun onErrorConnection() {
        materialDialog!!.dismiss()

    }

    override fun onResponseNotOk(message: String?) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        materialDialog!!.dismiss()
    }

    override fun onChangePage(page: Int) {
        presenter!!.getPopularMovies(page)
        callDialog()
    }

    override fun onClickImage(id: Long) {
        presenter!!.getMovieDetails(id)
        callDialog()
    }

    fun copyListMovie(listMovieItem: List<MovieItem>):List<Item>{
        val list:MutableList<Item> = arrayListOf()

        for(movieItem in listMovieItem){
            val item = Item(movieItem.originalTitle,movieItem.id,movieItem.voteAverage,movieItem.posterPath)
            list!!.add(item)
        }

        return list!!
    }

    fun copyDetails(moviesDetails: MoviesDetails):Details{
        val details = Details(moviesDetails.id,moviesDetails.original_title,moviesDetails.overview,
            moviesDetails.posterPath,moviesDetails.runtime,
            moviesDetails.vote,moviesDetails.genres,true)

        return  details
    }

    fun callDialog(){
        materialDialog = MaterialDialog.Builder(context!!)
            .content("Loading")
            .progress(true, 0)
            .cancelable(false)
            .canceledOnTouchOutside(false)
            .show()
    }



}