package com.unava.dia.trellolightmvp.ui.task

import androidx.lifecycle.*
import com.unava.dia.trellolightmvp.api.entity.Task
import com.unava.dia.trellolightmvp.api.useCases.TaskUseCase

class TaskActivityPresenter(private var useCase: TaskUseCase) :
    TaskActivityContract.TaskActivityPresenter,
    LifecycleOwner {

    var view: TaskActivityContract.TaskActivityView? = null
    private var viewLifecycle: Lifecycle? = null

    override fun getLifecycle(): Lifecycle {
        return this.viewLifecycle!!
    }

    override fun attachView(view: TaskActivityContract.TaskActivityView, viewLifecycle: Lifecycle) {
        this.view = view
        this.viewLifecycle = viewLifecycle

        viewLifecycle.addObserver(this)
        //view.updateUi()
    }

    override fun detachView(view: TaskActivityContract.TaskActivityView) {
        this.view = null
        this.viewLifecycle = null
    }

    override fun onBtDeleteClicked(taskfId: Int) {
        this.deleteTask(taskfId)
    }

    override fun onBtSaveClicked(taskfId: Int, title: String, description: String, boardId: Int) {
        if (taskfId == 0) {
            this.insertTask(Task(title, description, boardId))
        } else {
            this.getTask(taskfId).observe(this,
                Observer<Task> { t ->
                    if (t != null) {
                        t.title = title
                        t.description = description
                        this.updateTask(t)
                    }
                }
            )
        }

    }

    override fun loadUi(taskfId: Int) {
        this.getTask(taskfId).observe(this,
            Observer<Task> { task ->
                if (task != null) {
                    view?.updateUi(task)
                }
            }
        )
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun destroy() {
        viewLifecycle?.removeObserver(this)
        view = null
        viewLifecycle = null
    }

    private fun getTask(id: Int): LiveData<Task> {
        return this.useCase.getTask(id)
    }

    private fun deleteTask(id: Int) {
        this.useCase.deleteTask(id)
    }

    private fun updateTask(task: Task) {
        this.useCase.updateTask(task)
    }

    private fun insertTask(task: Task) {
        this.useCase.insertTask(task)
    }
}