package com.unava.dia.trellolightmvp.ui.board

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import com.unava.dia.trellolightmvp.api.entity.Board
import com.unava.dia.trellolightmvp.api.entity.Task
import com.unava.dia.trellolightmvp.api.useCases.TasksUseCase

class BoardActivityPresenter(private var useCase: TasksUseCase) : BoardActivityContract.BoardActivityPresenter,
    LifecycleOwner {

    private var viewLifecycle: Lifecycle? = null
    var view: BoardActivityContract.BoardActivityView? = null

    override fun getLifecycle(): Lifecycle {
        return this.viewLifecycle!!
    }

    override fun attachView(
        view: BoardActivityContract.BoardActivityView,
        viewLifecycle: Lifecycle
    ) {
        this.view = view
        this.viewLifecycle = viewLifecycle

        viewLifecycle.addObserver(this)
        view.setupRecyclerView()
    }

    override fun detachView(view: BoardActivityContract.BoardActivityView) {
        this.view = null
        this.viewLifecycle = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun destroy() {
        viewLifecycle?.removeObserver(this)
        view = null
        viewLifecycle = null
    }

    override fun loadUi(boardId: Int?) {
        if (boardId != null) {
            getBoard(boardId)
            this.getBoard(boardId).observe(this,
                Observer<Board> { b ->
                    if (b != null) {
                        view?.updateBoardsTitle(b.title)
                    }
                }
            )
            // TODO wants boardId = intent.getIntExtra(BOARD_ID, 0)
            this.findReposForTask(boardId)?.observe(this, Observer<List<Task>> { taskList ->
                view?.updateTaskList(taskList)
            })
        }
    }

    override fun onBtSaveClicked(isNewBoard: Boolean, boardName: String, boardId: Int?) {
        if (isNewBoard) {
            this.insertBoard(boardName)
        }
        else {
            if(boardId != null) {
                this.getBoard(boardId).observe(this,
                    Observer<Board> { b ->
                        if (b != null) {
                            b.title = boardName
                            this.updateBoard(b)
                        }
                    }
                )
            }
            else view?.showError("Board id is null")
        }
    }

    override fun onBtDeleteClicked(boardId: Int) {
        this.useCase.deleteBoard(boardId)
    }

    override fun onBtAddClicked(isNewBoard: Boolean, boardName: String) {
        // save board if new
        if (isNewBoard) {
            this.insertBoard(boardName)
            //TODO set boardId
        }
    }

    override fun findAllTasks(): LiveData<List<Task>>? {
        return this.useCase.findAllTasksAsync()
    }

    override fun getBoard(id: Int) : LiveData<Board> {
        return this.useCase.getBoard(id)
    }

    private fun updateBoard(board: Board) {
        this.useCase.updateBoard(board)
    }

    private fun insertBoard(text: String) {
        this.useCase.insertBoard(Board(text))
    }

    private fun findReposForTask(boardId: Int) : LiveData<List<Task>>? {
        return this.useCase.findRepositoriesForTask(boardId)
    }

}