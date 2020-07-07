package com.unava.dia.trellolightmvp.di

import android.app.Application
import com.unava.dia.trellolightmvp.TrelloLightApp
import com.unava.dia.trellolightmvp.di.repository.RepositoryModule
import com.unava.dia.trellolightmvp.di.useCases.UseCasesModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBuilder::class,
        RepositoryModule::class,
        UseCasesModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: TrelloLightApp)
}