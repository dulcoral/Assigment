package com.backbase.assignment.di

import com.backbase.assignment.MainApplication
import com.backbase.assignment.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
        NetworkModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        FragmentBuildersModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<MainApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: MainApplication): AppComponent
    }
}