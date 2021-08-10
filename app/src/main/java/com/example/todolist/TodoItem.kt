package com.example.todolist

import java.util.Calendar

class TodoItem(var name: String) {
    var date = Calendar.getInstance()
    var dateString: String = getDateAsString()

    fun getDateAsString(): String {
        val year = date.get(Calendar.YEAR).toString()
        val month = date.get(Calendar.MONTH).toString()
        val day = date.get(Calendar.DAY_OF_MONTH).toString()
        return "$month/$day/$year"
    }
}