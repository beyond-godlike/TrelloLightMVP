package com.unava.dia.trellolightmvp.api.repository


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.unava.dia.trellolightmvp.api.AppDatabase
import com.unava.dia.trellolightmvp.api.entity.Task
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class TaskRepository(context: Context) {

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    private val db: AppDatabase = AppDatabase.getAppDataBase(context)!!

    private val tasksLiveData: MutableLiveData<List<Task>> = MutableLiveData()

    fun getTasks() = db.taskDao().getTasks()
    fun getTask(id: Int) = db.taskDao().getTask(id)

    private fun getTaskAsync(id: Int): Task? = runBlocking(Dispatchers.Default) {
        return@runBlocking async { db.taskDao().getTaskAsync(id) }.await()
    }

    fun insertTask(title: String, description: String, boardId: Int) {
        scope.launch  { db.taskDao().insertTask(Task(title, description, boardId)) }
    }

    fun insertTask(task: Task) {
        scope.launch  { db.taskDao().insertTask(task) }
    }

    fun updateTask(task: Task) {
        scope.launch  { db.taskDao().updateTask(task) }
    }

    fun deleteTask(task: Task) {
        scope.launch  { db.taskDao().deleteTask(task) }
    }

    fun deleteTask(id: Int) {
        val task = getTaskAsync(id)
        scope.launch  { task?.let { db.taskDao().deleteTask(it) } }
    }

    fun getTasksForBoardAsync(boardId: Int): List<Task>? = runBlocking(Dispatchers.Default) {
        return@runBlocking async { db.taskDao().getTasksForBoard(boardId) }.await()
    }
}