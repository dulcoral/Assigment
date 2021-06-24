package com.backbase.assignment.di

import com.backbase.assignment.ui.MovieDetailFragment
import com.backbase.assignment.ui.MovieHomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): MovieHomeFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): MovieDetailFragment
}