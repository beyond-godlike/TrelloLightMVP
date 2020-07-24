package com.unava.dia.trellolightmvp.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.unava.dia.trellolightmvp.api.entity.Task

class TaskDiffUtil(private var oldTaskList: List<Task>, private var newTaskList: List<Task>) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTaskList[oldItemPosition].id === newTaskList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTaskList[oldItemPosition] == newTaskList[newItemPosition]
    }

    override fun getNewListSize(): Int {
        return newTaskList.size
    }

    override fun getOldListSize(): Int {
        return oldTaskList.size
    }


    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}