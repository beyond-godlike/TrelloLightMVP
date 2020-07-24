package com.unava.dia.trellolightmvp.api.useCases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.unava.dia.trellolightmvp.api.entity.Board
import com.unava.dia.trellolightmvp.api.entity.Task
import com.unava.dia.trellolightmvp.api.repository.BoardRepository
import com.unava.dia.trellolightmvp.api.repository.TaskRepository
import javax.inject.Inject

class TasksUseCase @Inject constructor(
    private var boardRepository: BoardRepository,
    private var taskRepository: TaskRepository
) {

    fun findAllTasksAsync(): LiveData<List<Task>>? {
        return taskRepository.getTasks()
    }

    fun getBoard(id: Int): LiveData<Board> {
        return boardRepository.getBoard(id)
    }

    fun deleteBoard(id: Int) {
        boardRepository.deleteBoard(id)
    }

    fun updateBoard(board: Board) {
        boardRepository.updateBoard(board)
    }

    fun insertBoard(board: Board) : Long? {
        return boardRepository.insertBoard(board)
    }

    fun getTasksForBoardAsync(boardId: Int): List<Task>?  {
        return taskRepository.getTasksForBoardAsync(boardId)
    }
}