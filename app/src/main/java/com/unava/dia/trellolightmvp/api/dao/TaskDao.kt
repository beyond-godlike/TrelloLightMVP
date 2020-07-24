package com.unava.dia.trellolightmvp.api.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.unava.dia.trellolightmvp.api.entity.Task

@Dao
interface TaskDao {
    @Insert
    fun insertTask(note: Task): Long?

    @Query("SELECT * from Task")
    fun getTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE id =:taskId")
    fun getTask(taskId: Int): LiveData<Task>

    @Query("SELECT * FROM Task WHERE boardId =:boardId")
    fun getTasksForBoard(boardId: Int): List<Task>

    @Query("SELECT * FROM Task WHERE id =:taskId")
    fun getTaskAsync(taskId: Int): Task

    @Update
    fun updateTask(note: Task)

    @Delete
    fun deleteTask(note: Task)
}