package com.example.studz.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studz.R
import com.example.studz.ui.home.HomeFragment
import kotlinx.android.synthetic.main.tasks_reminder_list_item.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ReminderListAdapter(private val data: ArrayList<com.example.studz.Model.Tasks>?)
    : RecyclerView.Adapter<ReminderListAdapter.AdapterViewHolder>() {
    private val dateFormat: DateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    class AdapterViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        return AdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.tasks_reminder_list_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val currentItem = data?.get(position)
        val currentDate = Date()

        holder.itemView.apply {
            val timeString = HomeFragment().convertTime2(currentItem!!, currentDate)
            tasks_dialog_title_tv.text = timeString+ " | " +currentItem.tkTitle
            tasks_dialog_time_tv.text = dateFormat.format(currentItem.tkDueDate.toDate())
            tasks_dialog_title_tv.setTextColor(Color.BLACK)
            tasks_dialog_time_tv.setTextColor(Color.BLACK)
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

}