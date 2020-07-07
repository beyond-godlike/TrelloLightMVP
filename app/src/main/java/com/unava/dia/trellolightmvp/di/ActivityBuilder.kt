package com.unava.dia.trellolightmvp.di

import com.unava.dia.trellolightmvp.di.subModules.*
import com.unava.dia.trellolightmvp.ui.board.BoardActivity
import com.unava.dia.trellolightmvp.ui.main.MainActivity
import com.unava.dia.trellolightmvp.ui.task.TaskActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [
        MainActivityViewModule::class,
        MainActivityModule::class
    ])
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [
        BoardActivityViewModule::class,
        BoardActivityModule::class
    ])
    internal abstract fun bindBoardActivity(): BoardActivity

    @ContributesAndroidInjector(modules = [
        TaskActivityViewModule::class,
        TaskActivityModule::class
    ])
    internal abstract fun bindTaskActivity(): TaskActivity
}