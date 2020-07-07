package com.unava.dia.trellolightmvp.api

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.unava.dia.trellolightmvp.api.dao.BoardDao
import com.unava.dia.trellolightmvp.api.dao.TaskDao
import com.unava.dia.trellolightmvp.api.entity.Board
import com.unava.dia.trellolightmvp.api.entity.Task
import com.unava.dia.trellolightmvp.utils.TaskListConverter

@Database(entities = [Board::class, Task::class], version = 1, exportSchema = false)
@TypeConverters(TaskListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun boardDao(): BoardDao
    abstract fun taskDao(): TaskDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "myDb"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}