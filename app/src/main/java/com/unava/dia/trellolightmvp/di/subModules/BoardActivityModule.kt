package com.unava.dia.trellolightmvp.di.subModules


import com.unava.dia.trellolightmvp.api.useCases.TasksUseCase
import com.unava.dia.trellolightmvp.ui.board.BoardActivityContract
import com.unava.dia.trellolightmvp.ui.board.BoardActivityPresenter
import dagger.Module
import dagger.Provides

@Module
class BoardActivityModule {
    @Provides
    fun provideMainActivityPresenter(useCase: TasksUseCase)
            : BoardActivityContract.BoardActivityPresenter = BoardActivityPresenter(useCase)
}