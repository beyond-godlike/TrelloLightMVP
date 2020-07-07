package com.unava.dia.trellolightmvp.di.subModules

import com.unava.dia.trellolightmvp.ui.board.BoardActivity
import com.unava.dia.trellolightmvp.ui.board.BoardActivityContract
import dagger.Binds
import dagger.Module

@Module
abstract class BoardActivityViewModule {
    @Binds
    internal abstract fun provideBoardActivityView(boardActivity: BoardActivity): BoardActivityContract.BoardActivityView
}