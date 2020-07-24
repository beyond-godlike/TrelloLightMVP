package com.unava.dia.trellolightmvp.ui.board

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import com.unava.dia.trellolightmvp.api.entity.Board
import com.unava.dia.trellolightmvp.api.entity.Task
import com.unava.dia.trellolightmvp.ui.base.BasePresenter
import com.unava.dia.trellolightmvp.ui.base.BaseView

interface BoardActivityContract {
    interface BoardActivityView : BaseView {
        var boardId: Int?
        fun updateBoardsTitle(title: String)
        fun updateTaskList(list: List<Task>)
        fun setupRecyclerView()
    }

    interface BoardActivityPresenter : BasePresenter<BoardActivityView>, LifecycleObserver {
        override fun detachView(view: BoardActivityView) { }
        override fun attachView(view: BoardActivityView, viewLifecycle: Lifecycle) {}
        override fun destroy() {    }

        fun loadUi(boardId: Int?)
        fun findAllTasks(): LiveData<List<Task>>?
        fun getBoard(id: Int) : LiveData<Board>

        fun onBtSaveClicked(isNewBoard: Boolean, boardName: String, boardId: Int?)
        fun onBtDeleteClicked(boardId: Int)
        fun onBtAddClicked(boardName: String)
    }
}