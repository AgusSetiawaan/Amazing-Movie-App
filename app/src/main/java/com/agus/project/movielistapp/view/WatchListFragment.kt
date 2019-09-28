package com.agus.project.movielistapp.view

import android.arch.lifecycle.Observer
import android.graphics.Point
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.agus.project.movielistapp.GridSpacingItemDecoration
import com.agus.project.movielistapp.R
import com.agus.project.movielistapp.database.AppDatabase
import com.agus.project.movielistapp.model.Item
import com.agus.project.movielistapp.model.MovieItem
import com.agus.project.movielistapp.model.WatchList
import com.agus.project.movielistapp.presenter.MoviesPresenter
import kotlinx.android.synthetic.main.movies_fragment.*
import kotlinx.android.synthetic.main.watchlist_fragment.*
import java.util.*

class WatchListFragment:Fragment(), MainActivity.MoviesListener{


    var _recyclerView: RecyclerView? = null
    var moviesAdapter: MoviesAdapter? = null
    var layout_null:RelativeLayout? = null
    var width = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view  =inflater.inflate(R.layout.watchlist_fragment,container,false)
        _recyclerView= view.findViewById(R.id.recycler_view)
        layout_null = view.findViewById(R.id.layout_kosong)
        moviesAdapter = MoviesAdapter(null,0,0,this,0)

        AppDatabase.getAppDatabase(context!!).daoWatchlist().getAllWwatchList().observe(this,object : Observer<List<WatchList>>{
            override fun onChanged(list: List<WatchList>?) {
                setRecyclerView(list!!)
            }

        })


//        Log.e("ini size list nya",""+list.size)



        return view



    }

    private fun setRecyclerView(list:List<WatchList>){
        Log.e("ini size list nya","tes")

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

        gridLayoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup(){
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



        if (list != null && list.isNotEmpty()){

            moviesAdapter!!.setMoviesList(copyListMovie(list),1,1,width)
            _recyclerView!!.visibility = View.VISIBLE
            layout_null!!.visibility = View.GONE
            moviesAdapter!!.notifyDataSetChanged()
            val layoutManager = _recyclerView!!.layoutManager as GridLayoutManager
            layoutManager.scrollToPositionWithOffset(0,0)

        }
    }

    override fun onChangePage(page: Int) {

    }

    override fun onClickImage(id: Long) {

    }

    fun copyListMovie(listMovieItem: List<WatchList>):List<Item>{
        val list:MutableList<Item> = arrayListOf()

        for(movieItem in listMovieItem){
            val item = Item(movieItem.title!!,movieItem.id_backend!!,movieItem.rate!!,movieItem.posterPath)
            list!!.add(item)
        }

        return list!!
    }
}