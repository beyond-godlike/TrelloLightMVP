package com.unava.dia.trellolightmvp.ui.board

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.unava.dia.trellolightmvp.R
import com.unava.dia.trellolightmvp.api.entity.Task
import com.unava.dia.trellolightmvp.ui.task.TaskActivity
import com.unava.dia.trellolightmvp.utils.AppConstants.Companion.ACTIVITY_REQUEST_CODE
import com.unava.dia.trellolightmvp.utils.AppConstants.Companion.BOARD_ID
import com.unava.dia.trellolightmvp.utils.AppConstants.Companion.NEW_BOARD
import com.unava.dia.trellolightmvp.utils.AppConstants.Companion.TASK_ID
import com.unava.dia.trellolightmvp.utils.RecyclerItemClickListener
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_board.*
import javax.inject.Inject

class BoardActivity : AppCompatActivity(), BoardActivityContract.BoardActivityView,
    RecyclerItemClickListener.OnRecyclerViewItemClickListener {
    @Inject
    lateinit var presenter: BoardActivityContract.BoardActivityPresenter

    override var boardId: Int? = null
    private var isNewBoard: Boolean = false
    private var tasksListAdapter: TasksListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        AndroidInjection.inject(this)

        boardId = intent.getIntExtra(BOARD_ID, 0)
        isNewBoard = intent.getBooleanExtra(NEW_BOARD, false)

        btSaveBoard.setOnClickListener {
            presenter.onBtSaveClicked(isNewBoard, etBoardName!!.text.toString(), boardId)
            finish()
        }
        btDeleteBoard.setOnClickListener {
            presenter.onBtDeleteClicked(boardId!!)
            finish()
        }
        btAddCard.setOnClickListener {
            // save board if new
            if (isNewBoard) {
                presenter.onBtAddClicked(etBoardName!!.text.toString())
                isNewBoard = false
            }

            val intent = Intent(this@BoardActivity, TaskActivity::class.java)
            intent.putExtra(BOARD_ID, boardId!!)
            startActivityForResult(intent, ACTIVITY_REQUEST_CODE)
        }

        presenter.attachView(this, lifecycle)
        lifecycle.addObserver(presenter)
        presenter.loadUi(boardId)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        intent.putExtra(BOARD_ID, boardId)
        intent.putExtra(NEW_BOARD, isNewBoard)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        boardId = intent.getIntExtra(BOARD_ID, 0)
        isNewBoard = intent.getBooleanExtra(NEW_BOARD, false)
    }

    override fun updateBoardsTitle(title: String) {
        etBoardName!!.setText(title)
    }

    override fun updateTaskList(list: List<Task>) {
        if (list.isNotEmpty()) {
            if (tasksListAdapter == null) {
                tasksListAdapter =
                    TasksListAdapter(list.toMutableList())
                rvBoard.adapter = tasksListAdapter
                tasksListAdapter?.addTasks(list)
            }
        }
    }

    override fun setupRecyclerView() {
        rvBoard!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvBoard!!.addOnItemTouchListener(RecyclerItemClickListener(this, this))
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(parentView: View, childView: View, position: Int) {
        val intent = Intent(this, TaskActivity::class.java)
        val task = tasksListAdapter!!.getItem(position)
        intent.putExtra(TASK_ID, task.id)
        intent.putExtra(BOARD_ID, boardId)
        startActivityForResult(intent, ACTIVITY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // because finish() doesn`t works properly. I`ts shitty yep
            this.recreate()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView(this)
        presenter.destroy()
        lifecycle.removeObserver(presenter)
    }
}
