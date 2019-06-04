package com.example.listexapmle

import android.content.Context
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.animal_list_item.view.*

class ToDoAdapter(val items: ArrayList<String>, val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    //Täyttää näkymät
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(from(context).inflate(R.layout.animal_list_item, parent, false))
    }

    //Palauttaa eläinten määrän
    override fun getItemCount(): Int {
        return items.size
    }

    //Bindaa jokaisen eläimen AllayListasta omaan näkymään
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtAnimal.text = items[position]
    }
}

class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val txtAnimal = v.txt_animal
}