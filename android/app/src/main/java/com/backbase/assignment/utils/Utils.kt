package com.backbase.assignment.utils

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.applySchedulers(): Single<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun Int?.orZero(): Int = this ?: 0
