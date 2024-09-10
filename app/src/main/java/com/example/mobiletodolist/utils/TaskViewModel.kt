package com.example.mobiletodolist.utils


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobiletodolist.TaskItem
import java.util.UUID

class TaskViewModel: ViewModel() {
    var taskItemsList = MutableLiveData<MutableList<TaskItem>>()

    init {
        taskItemsList.value = mutableListOf()
    }

    fun addTaskItem(newTask: TaskItem){
        val list = taskItemsList.value
        list!!.add(0, newTask)
        taskItemsList.postValue(list)
    }

    fun updateTaskItem(id: UUID, desc: String){
        val list = taskItemsList.value
        val task = list!!.find{ it.id == id}!!
        task.description = desc
        taskItemsList.postValue(list)
    }

    fun changeChecked(taskItem: TaskItem){
        val list = taskItemsList.value
        val task = list!!.find{ it.id == taskItem.id}!!
        task.checked = !task.checked
        taskItemsList.postValue(list)
    }

    fun deleteTaskItem(id: UUID){
        val list = taskItemsList.value
        val task = list!!.find{ it.id == id}!!
        list!!.remove(task)
        taskItemsList.postValue(list)
    }
}