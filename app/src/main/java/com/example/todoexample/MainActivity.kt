package com.example.todoexample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listexapmle.AddItemActivity
import com.example.todoexample.models.ToDoListItem
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var todoItems: MutableList<ToDoListItem>
    val ADD_NEW_TODO = 2019

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        getItemsFromRealm(Realm.getDefaultInstance())
        
        rv_list.layoutManager = LinearLayoutManager(this)
        //rv_animal_list.layoutManager = GridLayoutManager(this, 2)

        rv_list.adapter = ToDoAdapter(todoItems, this)

        setRecyclerViewItemTouchListener()

        fab.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivityForResult(intent, ADD_NEW_TODO)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_NEW_TODO) {
            if (resultCode == Activity.RESULT_OK) {
                getItemsFromRealm(Realm.getDefaultInstance())
                rv_list.adapter = ToDoAdapter(todoItems, this)

            }
        }
    }

    fun getItemsFromRealm(realm: Realm){
        val realmResults = realm.where(ToDoListItem::class.java).findAll()
        todoItems = realm.copyFromRealm(realmResults)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setRecyclerViewItemTouchListener() {
        val itemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    viewHolder1: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {

                    val position = viewHolder.adapterPosition
                    val realm = Realm.getDefaultInstance()
                    realm.beginTransaction()
                    realm.where(ToDoListItem::class.java).equalTo("id", todoItems[position].id).findFirst()?.deleteFromRealm()
                    realm.commitTransaction()

                    todoItems.removeAt(position)
                    rv_list.adapter!!.notifyItemRemoved(position)
                }
            }

        //4
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rv_list)
    }
}
