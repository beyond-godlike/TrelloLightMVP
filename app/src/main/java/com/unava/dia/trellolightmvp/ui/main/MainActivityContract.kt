package com.unava.dia.trellolightmvp.ui.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import com.unava.dia.trellolightmvp.api.entity.Board
import com.unava.dia.trellolightmvp.ui.base.BasePresenter
import com.unava.dia.trellolightmvp.ui.base.BaseView

interface MainActivityContract {
    interface MainActivityView : BaseView {
        fun showBoards(boards: List<Board>)
        fun setupRecyclerView()
        fun changeRecyclerViewVisibility(visible: Boolean)
    }

    interface MainActivityPresenter : BasePresenter<MainActivityView>, LifecycleObserver {
        override fun detachView(view: MainActivityView) { }
        override fun attachView(view: MainActivityView, viewLifecycle: Lifecycle) {}
        override fun destroy() {    }
        fun loadBoards()
    }
}