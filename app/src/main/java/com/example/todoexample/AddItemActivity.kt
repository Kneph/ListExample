package com.example.listexapmle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todoexample.MainActivity
import com.example.todoexample.R
import com.example.todoexample.models.ToDoListItem
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add_item.*


class AddItemActivity : AppCompatActivity() {
    private val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
    }

    fun addItem(v: View) {
        // Get the current max id in the TodoListItem table
        var maxId = realm.where(ToDoListItem::class.java).max("id")

        // If there are no rows, currentId is null, so the next id must be 1
        // If currentId is not null, increment it by 1
        val nextId = if (maxId == null) 1 else maxId.toInt() + 1

        var todoItem = ToDoListItem(nextId, etxt_title.text.toString(), etxt_body.text.toString(), "")
        try {
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(todoItem)
            realm.commitTransaction()
            val intent = Intent(this, MainActivity::class.java)
            setResult(Activity.RESULT_OK, intent)
            finish()

        } catch (e: Exception) {
            println(e)
            Toast.makeText(this, "Something went wrong with adding", Toast.LENGTH_LONG).show()
            finish()
        }

    }

}

