package com.unava.dia.trellolightmvp.ui.task

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import com.unava.dia.trellolightmvp.api.entity.Task
import com.unava.dia.trellolightmvp.ui.base.BasePresenter
import com.unava.dia.trellolightmvp.ui.base.BaseView

interface TaskActivityContract {
    interface TaskActivityView : BaseView {
        fun updateUi(task: Task)
    }

    interface TaskActivityPresenter : BasePresenter<TaskActivityView>, LifecycleObserver {
        override fun detachView(view: TaskActivityView) { }
        override fun attachView(view: TaskActivityView, viewLifecycle: Lifecycle) { }
        override fun destroy() { }

        fun onBtDeleteClicked(taskfId: Int)
        fun onBtSaveClicked(taskfId: Int, title: String, description: String, boardId: Int)
        fun loadUi(taskfId: Int)
    }
}