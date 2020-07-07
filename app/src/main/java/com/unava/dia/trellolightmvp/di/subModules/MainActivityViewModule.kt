package com.unava.dia.trellolightmvp.di.subModules

import com.unava.dia.trellolightmvp.ui.main.MainActivity
import com.unava.dia.trellolightmvp.ui.main.MainActivityContract
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityViewModule {
    @Binds
    internal abstract fun provideMainActivityView(mainActivity: MainActivity): MainActivityContract.MainActivityView
}