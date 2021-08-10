package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class AddItemActivity : AppCompatActivity() {

    private lateinit var itemNameEditText: EditText
    private lateinit var titleTextView: TextView

    private var isNewItem = true
    private lateinit var oldItem: TodoItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        itemNameEditText = findViewById(R.id.text_input_edit_text)
        titleTextView = findViewById(R.id.edit_title_text_view)
        val itemName = intent.getStringExtra("ITEM_NAME")

        if (itemName != null) {
            itemNameEditText.setText(itemName)
            titleTextView.setText(R.string.edit_todo_message)

            oldItem = TodoItem(itemName)
            isNewItem = false
        }
    }

    public fun saveItemAction(view: View) {
        val itemName = itemNameEditText.text.toString()
        val newTodoItem = TodoItem(itemName)
        val dbo = DatabaseOperations(this)
        if (isNewItem) {
            dbo.addItem(dbo, newTodoItem)
        }else {
            dbo.updateItem(dbo, this.oldItem, newTodoItem)

        }
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    public fun cancelAction(view: View) {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}