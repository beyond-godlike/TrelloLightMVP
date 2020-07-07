package com.unava.dia.trellolightmvp.ui.main

import androidx.lifecycle.*
import com.unava.dia.trellolightmvp.api.entity.Board
import com.unava.dia.trellolightmvp.api.useCases.BoardsUseCase

class MainActivityPresenter(private var useCase: BoardsUseCase) : MainActivityContract.MainActivityPresenter,
    LifecycleOwner{

    var view: MainActivityContract.MainActivityView? = null
    private var viewLifecycle: Lifecycle? = null

    override fun getLifecycle(): Lifecycle {
        return this.viewLifecycle!!
    }

    override fun loadBoards() {
        this.useCase.findAllBoardsAsync()?.observe(this, Observer<List<Board>> { boards ->
            if (boards.isNotEmpty()) {
                view?.changeRecyclerViewVisibility(true)
                view?.showBoards(boards)
            } else
                view?.changeRecyclerViewVisibility(false)
        })
    }


    override fun detachView(view: MainActivityContract.MainActivityView) {
        this.view = null
        this.viewLifecycle = null
    }

    override fun attachView(view: MainActivityContract.MainActivityView, viewLifecycle: Lifecycle) {
        this.view = view
        this.viewLifecycle = viewLifecycle

        viewLifecycle.addObserver(this)
        view.setupRecyclerView()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun destroy() {
        viewLifecycle?.removeObserver(this)
        view = null
        viewLifecycle = null
    }

}