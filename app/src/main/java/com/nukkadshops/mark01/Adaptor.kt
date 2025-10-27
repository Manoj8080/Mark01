package com.nukkadshops.mark01

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class Adaptor(private val context: Context, private val elements : MutableList<Models>) : RecyclerView.Adapter<Adaptor.ExampleViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExampleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ExampleViewHolder(view)

    }

    override fun onBindViewHolder(
        holder: ExampleViewHolder,
        position: Int
    ) {
        val currentItem = elements[position]
        holder.title.text = currentItem.title

    }

    override fun getItemCount(): Int {
        return elements.size

    }

    inner class ExampleViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title : TextView = view.findViewById(R.id.headerText)


        }
    }
