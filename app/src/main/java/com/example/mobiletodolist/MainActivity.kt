package com.example.mobiletodolist


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobiletodolist.components.TaskForm
import com.example.mobiletodolist.databinding.ActivityMainBinding
import com.example.mobiletodolist.utils.TaskItemsAdapter
import com.example.mobiletodolist.utils.TaskItemsClickListener
import com.example.mobiletodolist.utils.TaskViewModel

class MainActivity : AppCompatActivity(), TaskItemsClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        binding.addButton.setOnClickListener {
            TaskForm(null).show(supportFragmentManager, "newTaskTag")
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setRecyclerView()
    }

    private fun setRecyclerView() {
        val mainActivity = this
        taskViewModel.taskItemsList.observe(this) {
            binding.toDoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemsAdapter(it, mainActivity)
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        TaskForm(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    override fun changeCheckedTaskItem(taskItem: TaskItem) {
        taskViewModel.changeChecked(taskItem)
    }

    override fun deleteTaskItem(taskItem: TaskItem) {
        taskViewModel.deleteTaskItem(taskItem.id)
    }
}