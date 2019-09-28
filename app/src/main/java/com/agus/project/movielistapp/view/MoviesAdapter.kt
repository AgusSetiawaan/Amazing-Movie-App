package com.agus.project.movielistapp.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.agus.project.movielistapp.R
import com.agus.project.movielistapp.network.Constant
import com.bumptech.glide.Glide
import java.util.ArrayList
import com.agus.project.movielistapp.model.Item


class MoviesAdapter (listMovieItem: List<Item>?, currentPage:Int,
                     lastPage:Int, listener:MainActivity.MoviesListener, width:Int ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object{
        val TYPE_FOOTER = 0
        val TYPE_ITEM = 1
    }

    private var listMovieItem:List<Item>? = null
    private var currentPage = 0
    private var last_page = 0
    private var listener:MainActivity.MoviesListener? = null
    var width = 0

    init {
        setMoviesList(listMovieItem,currentPage,lastPage,width)
        this.listener = listener
    }

    fun setMoviesList(listMovieItem: List<Item>?,currentPage: Int, last_page: Int, width: Int){
        this.currentPage = currentPage
        this.last_page = last_page
        this.width = width
        if (listMovieItem == null) {
            this.listMovieItem = ArrayList()
        } else {
            this.listMovieItem = listMovieItem
            notifyDataSetChanged()

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder:RecyclerView.ViewHolder? = null
        if (viewType == TYPE_ITEM) {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.activity_photo_line, viewGroup, false)
            viewHolder = MoviesViewHolder(view)
        } else if (viewType == TYPE_FOOTER) {
            val view = LayoutInflater.from(viewGroup.context).inflate(
                R.layout.footer_layout,
                viewGroup, false
            )
            viewHolder = FooterViewHolder(view)
        }

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return if (listMovieItem!!.size!=0) listMovieItem!!.size+1 else 0
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, pos: Int) {

        if (viewHolder is MoviesViewHolder){
            val item = listMovieItem!![pos]
            viewHolder.bind(item,width, listener!!)
        }

        else if(viewHolder is FooterViewHolder){
            viewHolder.bind(listMovieItem,currentPage,
                last_page, listener!!)
//            val layoutParams = viewHolder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
//            layoutParams.isFullSpan = true
        }




    }

    override fun getItemViewType(position: Int): Int {
        return if (isPositionFooter(position)) TYPE_FOOTER else TYPE_ITEM
    }

    private fun isPositionFooter(position: Int): Boolean {
        return position == itemCount - 1
    }


    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var title: TextView
        var image: ImageView
        var rating: TextView

        init {
            title = itemView.findViewById(R.id.title)
            image = itemView.findViewById(R.id.photo)
            rating = itemView.findViewById(R.id.rating)
        }
        fun bind(item: Item, width: Int, listener: MainActivity.MoviesListener){
            title.text = item.title
            image.layoutParams.height = 3*width/4
            if(item.posterPath !=null)
                Glide.with(itemView).load(Constant.IMAGE_URL+item.posterPath).into(image)
            rating.text = item.voteAverage.toString()

            image.setOnClickListener {
                listener.onClickImage(item.id)
            }

        }
    }

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var page: TextView
        var prev: Button
        var next: Button
        var first: Button
        var last: Button

        init {
            page = view.findViewById(R.id.txtPage)
            prev = view.findViewById(R.id.txtPrev)
            next = view.findViewById(R.id.txtNext)
            first = view.findViewById(R.id.txtFirst)
            last = view.findViewById(R.id.txtLast)
        }

        fun bind(list: List<Item>?, currentPage: Int, last_page: Int,callback:MainActivity.MoviesListener){
            if (list!!.size != 0 && currentPage!=0 && last_page!=0) {
                val endOfPage = last_page
                page.setText(currentPage.toString()+ " " + "of" + " " + endOfPage)
                if (currentPage == 1) {
                    prev.visibility = View.INVISIBLE
                    first.visibility = View.INVISIBLE
                } else {
                    first.visibility = View.VISIBLE
                    first.setOnClickListener { callback.onChangePage(1) }

                    prev.visibility = View.VISIBLE
                    prev.setOnClickListener { callback.onChangePage(currentPage - 1) }
                }

                if (currentPage == endOfPage) {
                    next.visibility = View.INVISIBLE
                    last.visibility = View.INVISIBLE
                } else {
                    last.visibility = View.VISIBLE
                    last.setOnClickListener { callback.onChangePage(endOfPage) }
                    next.visibility = View.VISIBLE
                    next.setOnClickListener { callback.onChangePage(currentPage + 1) }
                }
            } else {
                prev.visibility = View.INVISIBLE
                first.visibility = View.INVISIBLE
                next.visibility = View.INVISIBLE
                last.visibility = View.INVISIBLE
                page.visibility = View.INVISIBLE
            }
        }
    }

}