package com.unava.dia.trellolightmvp.di.useCases

import com.unava.dia.trellolightmvp.api.repository.BoardRepository
import com.unava.dia.trellolightmvp.api.repository.TaskRepository
import com.unava.dia.trellolightmvp.api.useCases.BoardsUseCase
import com.unava.dia.trellolightmvp.api.useCases.TaskUseCase
import com.unava.dia.trellolightmvp.api.useCases.TasksUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCasesModule {
    @Singleton
    @Provides
    fun provideBoardsUseCase(boardRepository: BoardRepository): BoardsUseCase {
        return BoardsUseCase(boardRepository)
    }

    @Singleton
    @Provides
    fun provideTasksUseCase(boardRepository: BoardRepository, taskRepository: TaskRepository): TasksUseCase {
        return TasksUseCase(boardRepository, taskRepository)
    }

    @Singleton
    @Provides
    fun provideTaskUseCase(taskRepository: TaskRepository): TaskUseCase {
        return TaskUseCase(taskRepository)
    }
}