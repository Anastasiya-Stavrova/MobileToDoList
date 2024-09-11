package com.example.mobiletodolist.utils


import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobiletodolist.TaskItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.UUID


class TaskViewModel: ViewModel() {
    var taskItemsList = MutableLiveData<MutableList<TaskItem>>()

    private lateinit var myContext: Context

    init {
        taskItemsList.value = mutableListOf()
    }

    fun addTaskItem(newTask: TaskItem){
        val list = taskItemsList.value
        list!!.add(0, newTask)
        taskItemsList.postValue(list)

        saveDataToJsonFile(myContext)
    }

    fun updateTaskItem(id: UUID, desc: String){
        val list = taskItemsList.value
        val task = list!!.find{ it.id == id}!!
        task.description = desc
        taskItemsList.postValue(list)

        saveDataToJsonFile(myContext)
    }

    fun changeChecked(taskItem: TaskItem){
        val list = taskItemsList.value
        val task = list!!.find{ it.id == taskItem.id}!!
        task.checked = !task.checked
        taskItemsList.postValue(list)

        saveDataToJsonFile(myContext)
    }

    fun deleteTaskItem(id: UUID){
        val list = taskItemsList.value
        val task = list!!.find{ it.id == id}!!
        list!!.remove(task)
        taskItemsList.postValue(list)

        saveDataToJsonFile(myContext)
    }

    fun changeTaskItemsList(file: File){
        val json = file.readText()
        val type = object : TypeToken<MutableList<TaskItem>>() {}.type
        taskItemsList.value = Gson().fromJson(json, type)

        saveDataToJsonFile(myContext)
    }

    fun loadData(context: Context) {
        val file = File(context.filesDir, "taskItems.json")

        if (file.exists()) {
            val json = file.readText()
            val type = object : TypeToken<MutableList<TaskItem>>() {}.type
            taskItemsList.value = Gson().fromJson(json, type)
        }

        myContext = context
    }

    fun saveDataToJsonFile(context: Context) {
        val list = taskItemsList.value

        val gson = Gson()
        val jsonString = gson.toJson(list)

        val file = File(context.filesDir, "taskItems.json")
        file.writeText(jsonString)
    }
}