package com.agus.project.movielistapp.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import com.agus.project.movielistapp.R
import com.agus.project.movielistapp.database.AppDatabase
import com.agus.project.movielistapp.model.Details
import com.agus.project.movielistapp.model.WatchList
import com.agus.project.movielistapp.network.Constant
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movies_details.*

class MovieDetailActivity: AppCompatActivity() {

    var genres = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movies_details)



        val details = intent.extras.getSerializable("movie") as? Details

        val listNames:MutableList<String> = arrayListOf()

        title_movie.text = details!!.title
        rate_movie.text = details.vote.toString()+"/10"

        details.genres.forEach {
            listNames.add(it.name)
        }

        genres = listNames.joinToString (
            separator = ", "
        )

        genre_movie.text = genres
        duration.text = (details.runtime/60).toString()+" jam "+(details.runtime%60)+" menit"
        synopsis.text = details.overview

        if(details.posterPath !=null)
            Glide.with(this).load(Constant.IMAGE_URL+details.posterPath).into(movie_images)

        button_favorite.setOnCheckedChangeListener(object:View.OnClickListener, CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
                if(isChecked)
                    AppDatabase.getAppDatabase(applicationContext).daoWatchlist().insert(copyToWatchList(details))
                else{
                    AppDatabase.getAppDatabase(applicationContext).daoWatchlist().deleteByIdBackend(details.id,details.isMovies)
                }
            }

            override fun onClick(p0: View?) {
            }
        });

        back_btn.setOnClickListener {
            onBackPressed()
        }
    }

    fun copyToWatchList(details: Details):WatchList{
        return WatchList(details.id,details.title,details.overview,
            details.posterPath,details.runtime,genres,details.isMovies,details.vote)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }



}