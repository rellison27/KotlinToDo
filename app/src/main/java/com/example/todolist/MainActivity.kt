package com.example.todolist

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var toDoItemRecyclerView: RecyclerView
    private lateinit var recyclerAdapter: TodoItemsAdapter
    private lateinit var recyclerLayoutManager: RecyclerView.LayoutManager
    private  lateinit var todaysItemsButton: Button
    private  lateinit var pastItemsButton: Button

    var todoItemsList = ArrayList<TodoItem>()
    var todaysItemsList = ArrayList<TodoItem>()
    var pastItemsList = ArrayList<TodoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbo = DatabaseOperations(this)
        val cursor = dbo.getAllItems(dbo)
        with(cursor) {
            while (moveToNext()) {
                val itemName = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME))
                val itemDate = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_DATE))

                val newTodoItems = TodoItem(itemName)
                newTodoItems.dateString = itemDate
                todoItemsList.add(newTodoItems)
                if(getDateAsString() == itemDate) {
                    todaysItemsList.add(newTodoItems)
                } else {
                    pastItemsList.add(newTodoItems)
                }
            }
        }

        toDoItemRecyclerView = findViewById(R.id.todo_item_recycler_view)
        todaysItemsButton = findViewById(R.id.todays_items_button)
        pastItemsButton = findViewById(R.id.past_items_button)

        recyclerLayoutManager = LinearLayoutManager(this)
        recyclerAdapter = TodoItemsAdapter(todoItemsList, this)

        toDoItemRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdapter
        }
    }

//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public fun displayTodaysItems(view: View) {
        recyclerAdapter = TodoItemsAdapter(todaysItemsList, this)

        toDoItemRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdapter
        }
        todaysItemsButton.background = getDrawable(R.color.teal_200)
        pastItemsButton.background = getDrawable(R.color.purple_500)
        //            android:backgroundTint="@color/teal_700"
    }

//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public fun displayPastItems(view: View) {
        recyclerAdapter = TodoItemsAdapter(pastItemsList, this)

        toDoItemRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdapter
        }
        pastItemsButton.background = getDrawable(R.color.teal_200)
        todaysItemsButton.background = getDrawable(R.color.purple_500)
    }

    public fun navToAddItemAction(view: View) {
        val intent: Intent = Intent(this, AddItemActivity::class.java)
        startActivity(intent)
    }

    fun getDateAsString(): String {
        val date = Calendar.getInstance()
        val year = date.get(Calendar.YEAR).toString()
        val month = date.get(Calendar.MONTH).toString()
        val day = date.get(Calendar.DAY_OF_MONTH).toString()
        return "$month/$day/$year"
    }
}