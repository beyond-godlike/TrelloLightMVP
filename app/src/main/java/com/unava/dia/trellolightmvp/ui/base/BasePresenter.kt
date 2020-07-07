package com.unava.dia.trellolightmvp.ui.base

import androidx.lifecycle.Lifecycle

interface BasePresenter<in V : BaseView>{
    fun detachView(view: V)
    fun attachView(view: V, viewLifecycle: Lifecycle)
    fun destroy()
}