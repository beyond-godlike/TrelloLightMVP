package com.unava.dia.trellolightmvp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.unava.dia.trellolightmvp.R
import com.unava.dia.trellolightmvp.api.entity.Board
import com.unava.dia.trellolightmvp.ui.board.BoardActivity
import com.unava.dia.trellolightmvp.utils.AppConstants.Companion.BOARD_ID
import com.unava.dia.trellolightmvp.utils.AppConstants.Companion.NEW_BOARD
import com.unava.dia.trellolightmvp.utils.RecyclerItemClickListener
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainActivityContract.MainActivityView,
    RecyclerItemClickListener.OnRecyclerViewItemClickListener {

    @Inject
    lateinit var presenter: MainActivityContract.MainActivityPresenter
    private var boardsListAdapter: BoardsListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)

        fab.setOnClickListener { onFabAddBoardClicked() }
        presenter.attachView(this, lifecycle)
        lifecycle.addObserver(presenter)
        presenter.loadBoards()
    }


    override fun setupRecyclerView() {
        val displayMetrics = this.applicationContext.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val columns = (dpWidth / (200 + 20)).toInt()

        rvMain!!.layoutManager =
            StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL)
        rvMain!!.addOnItemTouchListener(RecyclerItemClickListener(this, this))
    }


    override fun changeRecyclerViewVisibility(visible: Boolean) {
        if(visible) rvMain!!.visibility = View.VISIBLE
        else rvMain!!.visibility = View.GONE
    }

    override fun showBoards(boards: List<Board>) {
        if (boardsListAdapter == null) {
            boardsListAdapter =
                BoardsListAdapter(boards.toMutableList())
            rvMain!!.adapter = boardsListAdapter
        } else
        boardsListAdapter!!.addBoards(boards)
    }

    private fun onFabAddBoardClicked() {
        val intent = Intent(this@MainActivity, BoardActivity::class.java)
        intent.putExtra(NEW_BOARD, true)
        startActivity(intent)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(parentView: View, childView: View, position: Int) {
        val intent = Intent(this, BoardActivity::class.java)
        val board = boardsListAdapter!!.getItem(position)
        intent.putExtra(BOARD_ID, board.id)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView(this)
        presenter.destroy()
        lifecycle.removeObserver(presenter)
    }

}
