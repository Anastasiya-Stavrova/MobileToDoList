package com.example.mobiletodolist.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobiletodolist.TaskItem
import java.time.LocalDate
import java.util.UUID

class TaskViewModel: ViewModel() {
    var taskItemsList = MutableLiveData<MutableList<TaskItem>>()

    init {
        taskItemsList.value = mutableListOf()
    }

    fun addTaskItem(newTask: TaskItem){
        val list = taskItemsList.value
        list!!.add(newTask)
        taskItemsList.postValue(list)
    }

    fun updateTaskItem(id: UUID, desc: String){
        val list = taskItemsList.value
        val task = list!!.find{ it.id == id}!!
        task.description = desc
        taskItemsList.postValue(list)
    }
}