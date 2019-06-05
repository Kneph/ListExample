package com.example.todoexample

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.todoexample.models.ToDoListItem
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add_item.*


class AddItemActivity : AppCompatActivity() {

    private val realm = Realm.getDefaultInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
    }

    fun addItem(v: View) {

        if (etxt_title.text.isEmpty() || etxt_body.text.isEmpty()) {

            var alertDialog = AlertDialog.Builder(this)
                .setTitle(this.resources.getString(R.string.empty_field_title))
                .setMessage(this.resources.getString(R.string.empty_field))
                .setPositiveButton(this.resources.getString(R.string.yes)) { dialog, which ->

                    createSnackbar(v)
                    
                }
                .setNegativeButton(this.resources.getString(R.string.no)) { dialog, which ->
                    // sit tehää jotain
                    addItemToRealm()
                    Toast.makeText(this, "Ok sit tehää niin", Toast.LENGTH_LONG).show()
                }
            alertDialog.show()

        } else {
            addItemToRealm()
        }

    }

    private fun createSnackbar(v: View) {
        val snackbar = Snackbar.make(v, this.resources.getString(R.string.cool), Snackbar.LENGTH_LONG)
            .setAction(this.resources.getString(R.string.changed_mind)) {
                addItemToRealm()
            }
            .setActionTextColor(Color.YELLOW)
        // Emme voi muuttaa Snackbarin ilmoitustekstin väriä ilman että etsimme sen id:n kautta
        val snackbarTextView = snackbar.view.findViewById<TextView>(R.id.snackbar_text)
        snackbarTextView.setTextColor(ContextCompat.getColor(this, R.color.snackbarTextColor))
        snackbar.show()
    }

    private fun addItemToRealm() {

        var maxId = realm.where(ToDoListItem::class.java).max("id")

        var nextId = if (maxId == null) 1 else maxId.toInt() + 1

        var todoItem = ToDoListItem(nextId, etxt_title.text.toString(), etxt_body.text.toString())

        try {
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(todoItem)
            realm.commitTransaction()
            val intent = Intent(this, MainActivity::class.java)
            setResult(Activity.RESULT_OK, intent)

        } catch (e: Exception) {
            println(e)
        }
        finish()

    }


}
