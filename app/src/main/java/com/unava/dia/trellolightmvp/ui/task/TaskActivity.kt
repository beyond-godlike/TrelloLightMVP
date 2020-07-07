package com.unava.dia.trellolightmvp.ui.task

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.unava.dia.trellolightmvp.R
import com.unava.dia.trellolightmvp.api.entity.Task
import com.unava.dia.trellolightmvp.utils.AppConstants.Companion.BOARD_ID
import com.unava.dia.trellolightmvp.utils.AppConstants.Companion.TASK_ID
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_task.*
import javax.inject.Inject

class TaskActivity : AppCompatActivity(), TaskActivityContract.TaskActivityView {

    @Inject
    lateinit var presenter: TaskActivityContract.TaskActivityPresenter

    private var boardId = 0
    private var taskfId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        AndroidInjection.inject(this)
        boardId = intent.getIntExtra(BOARD_ID, 0)
        taskfId = intent.getIntExtra(TASK_ID, 0)

        presenter.attachView(this, lifecycle)
        lifecycle.addObserver(presenter)

        btDelete.setOnClickListener {
            presenter.onBtDeleteClicked(taskfId)
            finish()
        }
        btDone.setOnClickListener {
            presenter.onBtSaveClicked(
                taskfId,
                etTitle!!.text.toString(),
                etDesc!!.text.toString(),
                boardId
            )
            finish()
        }
        presenter.loadUi(taskfId)
    }

    override fun updateUi(task: Task) {
        etTitle!!.setText(task.title)
        etDesc!!.setText(task.description)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView(this)
        presenter.destroy()
        lifecycle.removeObserver(presenter)
    }
}
