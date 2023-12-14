package com.example.titanapi.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserLog::class, ImageResponses::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userLogDao(): UserLogDAO
    abstract fun imageDao(): ImageDAO
    
}