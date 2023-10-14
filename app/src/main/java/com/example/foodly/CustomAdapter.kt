package com.example.foodly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter (private var items: MutableList<Item>, private val clickListener: (Item) -> Unit):
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, clickListener)
    }

    override fun getItemCount() = items.size

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Item, clickListener: (Item) -> Unit) {
            val imagen = view.findViewById<ImageView>(R.id.imv)
            val tv_tittle = view.findViewById<TextView>(R.id.tv_titulo)
            val tv_categoria = view.findViewById<TextView>(R.id.tv_categoria)

            imagen.setImageResource(item.imageResId)
            tv_tittle.text = item.titulo
            tv_categoria.text = item.categoria

            view.setOnClickListener { clickListener(item) }
        }
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }


}