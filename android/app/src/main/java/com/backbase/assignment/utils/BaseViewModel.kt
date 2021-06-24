package com.backbase.assignment.utils

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val disposable = CompositeDisposable()

    val showError = SingleLiveEvent<String>()

    override fun onCleared() {
        disposable.clear()
    }

}