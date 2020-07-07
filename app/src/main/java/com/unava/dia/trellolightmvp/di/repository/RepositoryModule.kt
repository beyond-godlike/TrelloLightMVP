package com.unava.dia.trellolightmvp.di.repository

import android.app.Application
import android.content.Context
import com.unava.dia.trellolightmvp.api.AppDatabase
import com.unava.dia.trellolightmvp.api.dao.BoardDao
import com.unava.dia.trellolightmvp.api.dao.TaskDao
import com.unava.dia.trellolightmvp.api.repository.BoardRepository
import com.unava.dia.trellolightmvp.api.repository.TaskRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return AppDatabase.getAppDataBase(application.applicationContext)!!
    }

    @Provides
    @Singleton
    fun provideBoardDao(appDatabase: AppDatabase): BoardDao {
        return appDatabase.boardDao()
    }

    @Provides
    @Singleton
    fun provideTaskDao(appDatabase: AppDatabase): TaskDao {
        return appDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun provideBoardRepository(context: Context): BoardRepository {
        return BoardRepository(context)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(context: Context): TaskRepository {
        return TaskRepository(context)
    }
}