package com.agus.project.movielistapp.view

import android.arch.persistence.room.Room
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.widget.Toast
import com.agus.project.movielistapp.R
import com.agus.project.movielistapp.database.AppDatabase

class MainActivity : AppCompatActivity() {

    var viewPager:ViewPager? = null
    var tabLayout:TabLayout? = null
    var toolbar:Toolbar? = null
    var db:AppDatabase? = null
    var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java!!, "movies").allowMainThreadQueries()
            .build()

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""

        setupViewPager(viewPager!!)
        tabLayout!!.setTabTextColors(ContextCompat.getColorStateList(this, R.color.white))
        tabLayout!!.setupWithViewPager(viewPager)
    }

    fun setupViewPager(viewPager: ViewPager){
        val adapter = TabMoviesAdapter(supportFragmentManager)
        val fragment1 = MoviesFragment()
        val fragment2 = TvFragment()
        val fragment3 = WatchListFragment()


        adapter.addFragment(fragment1, "Movies")
        adapter.addFragment(fragment2, "TV Series")
        adapter.addFragment(fragment3,"Watchlist")

        viewPager.adapter = adapter
    }

    interface MoviesListener{
        fun onChangePage(page:Int)
        fun onClickImage(id:Long)
    }

    override fun onBackPressed() {
        if(doubleBackToExitPressedOnce){
//            super.onBackPressed()
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        else{
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this,"Please click BACK again to exit",Toast.LENGTH_SHORT).show()
        }



        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false },2000)
    }



}
