package com.unava.dia.trellolightmvp.di.subModules

import com.unava.dia.trellolightmvp.api.useCases.BoardsUseCase
import com.unava.dia.trellolightmvp.ui.main.MainActivityContract
import com.unava.dia.trellolightmvp.ui.main.MainActivityPresenter
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideMainActivityPresenter(useCase: BoardsUseCase)
            : MainActivityContract.MainActivityPresenter = MainActivityPresenter(useCase)

}