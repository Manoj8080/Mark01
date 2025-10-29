package com.nukkadshops.mark01

import android.content.Context
import android.health.connect.datatypes.units.Length
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class Adaptor(
    private val context: Context,
    private val originalList: MutableList<Models>,
    private val onItemClickListener: (Models) -> Unit
) : RecyclerView.Adapter<Adaptor.ExampleViewHolder>() {

    // Maintain a filtered copy of the data
    private var filteredList: MutableList<Models> = ArrayList(originalList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_layout, parent, false)
        return ExampleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = filteredList[position]
        holder.title.text = currentItem.title
//        holder.itemView.setOnClickListener {
//            onItemClickListener(currentItem)
//        }
        holder.cardView.setOnClickListener {
            onItemClickListener(currentItem)
            //Toast.makeText(context,"youre second avctivity is succesful",Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = filteredList.size

    inner class ExampleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.headerText)
        val cardView: CardView=view.findViewById(R.id.headerCard)

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
