package com.example.mobiletodolist

import android.content.Context
import androidx.core.content.ContextCompat
import java.util.UUID


class TaskItem(
    var description: String,
    var checked: Boolean = false,
    var id: UUID = UUID.randomUUID()
) {

    fun imageResource(): Int {
        if(checked){
            return R.drawable.radio_checked
        }
        return R.drawable.radio_unchecked
    }

    fun imageColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.main_color)
    }

    fun changeChecked() {
        checked = !checked
    }

    fun changeDescription(description: String){
        this.description = description
    }
}