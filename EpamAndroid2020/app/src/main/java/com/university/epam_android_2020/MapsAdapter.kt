package com.university.epam_android_2020

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.university.epam_android_2020.user_data.Group

class MapsAdapter(val context: Context, val groups: MutableList<String?>, val onClickListener: OnClickListener) : RecyclerView.Adapter<MapsAdapter.ViewHolder>() {
    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    override fun getItemCount() = groups.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mapOfUsers = groups[position]
        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(position)
        }
        val textViewTitle = holder.itemView.findViewById<TextView>(android.R.id.text1)
        textViewTitle.text = mapOfUsers
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
