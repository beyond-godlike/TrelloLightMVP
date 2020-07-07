package com.unava.dia.trellolightmvp.di.subModules

import com.unava.dia.trellolightmvp.ui.task.TaskActivity
import com.unava.dia.trellolightmvp.ui.task.TaskActivityContract
import dagger.Binds
import dagger.Module

@Module
abstract class TaskActivityViewModule {
    @Binds
    internal abstract fun provideTaskActivityView(mainActivity: TaskActivity): TaskActivityContract.TaskActivityView
}