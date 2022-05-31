package com.example.moviemvi.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviemvi.repository.room.dao.AppDao
import com.example.moviemvi.repository.room.entity.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {
        private var appDatabase: AppDatabase? = null
        private const val DB_NAME = "AppDb"
        fun getInstance(context: Context): AppDatabase {
            return appDatabase ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                appDatabase = instance
                instance
            }
        }
    }
}