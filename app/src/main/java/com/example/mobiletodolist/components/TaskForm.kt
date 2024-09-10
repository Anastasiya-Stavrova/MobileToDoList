package com.example.mobiletodolist.components


import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mobiletodolist.TaskItem
import com.example.mobiletodolist.databinding.FragmentNewTaskSheetBinding
import com.example.mobiletodolist.utils.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class TaskForm(var taskItem: TaskItem?): BottomSheetDialogFragment()
{

    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var activity = requireActivity()
        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)

        if(taskItem != null) {
            binding.taskTitle.text = "Редактировать задачу"

            val editable = Editable.Factory.getInstance()
            binding.taskDesc.text = editable.newEditable(taskItem!!.description)
        }
        else {
            binding.taskTitle.text = "Добавить задачу"
        }

        binding.saveButton.setOnClickListener {
            saveAction()
        }
    }

    private fun saveAction() {
        val description = binding.taskDesc.text.toString()

        if (taskItem == null) {
            val newTaskItem = TaskItem(description)
            taskViewModel.addTaskItem(newTaskItem)
        }
        else {
            taskViewModel.updateTaskItem(taskItem!!.id, taskItem!!.description)
        }

        binding.taskDesc.setText("")

        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskSheetBinding.inflate(inflater, container, false)
        return  binding.root
    }
}