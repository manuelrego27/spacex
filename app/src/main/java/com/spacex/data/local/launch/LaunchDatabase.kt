package com.spacex.data.local.launch

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.spacex.data.entities.Launch

@Database(entities = [Launch::class], version = 1, exportSchema = false)
@TypeConverters(LaunchConverters::class)
abstract class LaunchDatabase : RoomDatabase() {

    abstract fun launchDao(): LaunchDao

    companion object {
        @Volatile
        private var INSTANCE: LaunchDatabase? = null

        fun getInstance(context: Context): LaunchDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, LaunchDatabase::class.java, "launch_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
