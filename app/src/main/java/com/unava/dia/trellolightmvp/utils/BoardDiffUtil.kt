package com.unava.dia.trellolightmvp.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.unava.dia.trellolightmvp.api.entity.Board

class BoardDiffUtil(private var oldBoardList: List<Board>, private var newBoardList: List<Board>)
    : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldBoardList[oldItemPosition].id === newBoardList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldBoardList[oldItemPosition] == newBoardList[newItemPosition]
    }

    override fun getNewListSize(): Int {
        return newBoardList.size
    }

    override fun getOldListSize(): Int {
        return oldBoardList.size
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}