package com.backbase.assignment.di

import com.backbase.assignment.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [FragmentBuildersModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity
}