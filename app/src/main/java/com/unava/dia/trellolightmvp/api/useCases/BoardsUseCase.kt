package com.unava.dia.trellolightmvp.api.useCases

import androidx.lifecycle.LiveData
import com.unava.dia.trellolightmvp.api.entity.Board
import com.unava.dia.trellolightmvp.api.repository.BoardRepository
import javax.inject.Inject

class BoardsUseCase @Inject constructor(private var boardRepository: BoardRepository) {
    fun findAllBoardsAsync(): LiveData<List<Board>>? {
        return boardRepository.getBoards()
    }
}