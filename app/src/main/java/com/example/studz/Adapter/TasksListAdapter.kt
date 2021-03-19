package com.example.studz.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studz.Model.Tasks
import com.example.studz.R
import kotlinx.android.synthetic.main.tasks_reminder_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class TasksListAdapter(private val data: ArrayList<Tasks>?)
    : RecyclerView.Adapter<TasksListAdapter.AdapterViewHolder>() {
    private val timeStampFormat2 = SimpleDateFormat("dd/MM h:mm a", Locale.getDefault())

    class AdapterViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        return AdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.tasks_reminder_list_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val currentItem = data?.get(position)
        holder.itemView.apply {
            tasks_dialog_title_tv.text = "- ${currentItem!!.tkTitle}"
            tasks_dialog_time_tv.text = timeStampFormat2.format(currentItem.tkDueDate.toDate())
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

}