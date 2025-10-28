package com.nukkadshops.mark01

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adaptor(
    private val context: Context,
    private val originalList: MutableList<Models>
) : RecyclerView.Adapter<Adaptor.ExampleViewHolder>() {

    // Maintain a filtered copy of the data
    private var filteredList: MutableList<Models> = ArrayList(originalList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ExampleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = filteredList[position]
        holder.title.text = currentItem.title
    }

    override fun getItemCount(): Int = filteredList.size

    inner class ExampleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.headerText)
    }

    // 🔹 Called when user types in search
    fun updateList(newList: List<Models>) {
        filteredList.clear()
        filteredList.addAll(newList)
        notifyDataSetChanged()
    }

    // 🔹 Called when search is cleared
    fun resetList() {
        filteredList.clear()
        filteredList.addAll(originalList)
        notifyDataSetChanged()
    }
}
