package com.unava.dia.trellolightmvp.api.useCases

import androidx.lifecycle.LiveData
import com.unava.dia.trellolightmvp.api.entity.Task
import com.unava.dia.trellolightmvp.api.repository.TaskRepository
import javax.inject.Inject

class TaskUseCase @Inject constructor(private var taskRepository: TaskRepository) {

    fun getTask(id: Int): LiveData<Task> {
        return taskRepository.getTask(id)
    }

    fun deleteTask(id: Int) {
        taskRepository.deleteTask(id)
    }

    fun updateTask(task: Task) {
        taskRepository.updateTask(task)
    }

    fun insertTask(task: Task) {
        taskRepository.insertTask(task)
    }

}