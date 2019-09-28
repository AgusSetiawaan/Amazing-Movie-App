package com.agus.project.movielistapp.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.agus.project.movielistapp.model.WatchList
import android.arch.persistence.room.Room
import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import com.agus.project.movielistapp.network.Constant

@Database(entities = [WatchList::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun daoWatchlist():Daowatchlist

    companion object{
        private var INSTANCE: AppDatabase? = null
        @JvmStatic fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase::class.java, Constant.DATABASE)
                    .allowMainThreadQueries().build()

                //Room.inMemoryDatabaseBuilder(context.getApplicationContext(),AppDatabase.class).allowMainThreadQueries().build();
            }
            return INSTANCE!!
        }

        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }

    }


}
