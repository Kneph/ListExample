package com.example.todoexample

import android.content.Context
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todoexample.models.ToDoListItem
import kotlinx.android.synthetic.main.todo_list_item.view.*

class ToDoAdapter(val items: ArrayList<ToDoListItem>, val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    //Täyttää näkymät
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(from(context).inflate(R.layout.todo_list_item, parent, false))
    }

    //Palauttaa eläinten määrän
    override fun getItemCount(): Int {
        return items.size
    }

    //Bindaa jokaisen eläimen AllayListasta omaan näkymään
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtTitle.text = items.get(position).title
        holder.txtBody.text = items.get(position).description
        if (items.get(position).imgUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context).load(items.get(position).imgUrl).into(holder.imageView)
        }
    }
}

class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val txtTitle = v.header
    val txtBody = v.body
    val imageView = v.image
}