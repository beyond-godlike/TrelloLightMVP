package com.unava.dia.trellolightmvp.di.subModules

import com.unava.dia.trellolightmvp.api.useCases.TaskUseCase
import com.unava.dia.trellolightmvp.ui.task.TaskActivityContract
import com.unava.dia.trellolightmvp.ui.task.TaskActivityPresenter
import dagger.Module
import dagger.Provides

@Module
class TaskActivityModule {
    @Provides
    fun provideTaskActivityPresenter(useCase: TaskUseCase)
            : TaskActivityContract.TaskActivityPresenter = TaskActivityPresenter(useCase)
}