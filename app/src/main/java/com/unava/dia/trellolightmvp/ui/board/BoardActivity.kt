package com.unava.dia.trellolightmvp.ui.board

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.unava.dia.trellolightmvp.R
import com.unava.dia.trellolightmvp.api.entity.Task
import com.unava.dia.trellolightmvp.ui.task.TaskActivity
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

    private var boardId: Int? = null
    private var tasksListAdapter: TasksListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        AndroidInjection.inject(this)

        boardId = intent.getIntExtra(BOARD_ID, 0)

        btSaveBoard.setOnClickListener {
            val isNewBoard = intent.getBooleanExtra(NEW_BOARD, false)
            presenter.onBtSaveClicked(isNewBoard, etBoardName!!.text.toString(), boardId)
            finish()
        }
        btDeleteBoard.setOnClickListener {
            presenter.onBtDeleteClicked(boardId!!)
            finish()
        }
        btAddCard.setOnClickListener {
            val isNewBoard = intent.getBooleanExtra(NEW_BOARD, false)
            presenter.onBtAddClicked(isNewBoard, etBoardName!!.text.toString())

            val intent = Intent(this@BoardActivity, TaskActivity::class.java)
            intent.putExtra(BOARD_ID, boardId!!)
            startActivity(intent)
        }

        presenter.attachView(this, lifecycle)
        lifecycle.addObserver(presenter)
        presenter.loadUi(boardId)
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
        rvBoard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvBoard.addOnItemTouchListener(RecyclerItemClickListener(this, this))
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(parentView: View, childView: View, position: Int) {
        val intent = Intent(this, TaskActivity::class.java)
        val task = tasksListAdapter!!.getItem(position)
        intent.putExtra(TASK_ID, task.id)
        intent.putExtra(BOARD_ID, boardId)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView(this)
        presenter.destroy()
        lifecycle.removeObserver(presenter)
    }
}
