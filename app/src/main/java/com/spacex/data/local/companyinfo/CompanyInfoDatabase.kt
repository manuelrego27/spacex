package com.spacex.data.local.companyinfo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.spacex.data.entities.CompanyInfo

@Database(entities = [CompanyInfo::class], version = 1, exportSchema = false)
abstract class CompanyInfoDatabase : RoomDatabase() {

    abstract fun companyInfoDao(): CompanyInfoDao

    companion object {
        @Volatile
        private var INSTANCE: CompanyInfoDatabase? = null

        fun getInstance(context: Context): CompanyInfoDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, CompanyInfoDatabase::class.java, "company_info")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}