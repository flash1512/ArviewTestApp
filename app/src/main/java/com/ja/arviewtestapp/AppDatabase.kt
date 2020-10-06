package com.ja.arviewtestapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TopGameEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTopGameEntityDao(): TopGameEntityDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = instance ?: synchronized(this) {
            instance ?: createDb(context).also { instance = it }
        }

        private fun createDb(context: Context): AppDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_db.db"
        ).build()
    }
}