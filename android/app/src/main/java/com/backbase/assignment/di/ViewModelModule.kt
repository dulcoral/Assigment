package com.backbase.assignment.di

import androidx.lifecycle.ViewModel
import com.backbase.assignment.ui.MovieDetailViewModel
import com.backbase.assignment.ui.MovieMainViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieMainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MovieMainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    internal abstract fun bindDetailViewModel(viewModel: MovieDetailViewModel): ViewModel
}