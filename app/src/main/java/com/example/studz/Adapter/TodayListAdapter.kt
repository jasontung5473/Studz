package com.example.studz.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studz.Model.Timetable
import com.example.studz.R
import com.example.studz.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.today_list_item.view.*
import java.util.ArrayList

class TodayListAdapter(private val data: ArrayList<Timetable>?)
    : RecyclerView.Adapter<TodayListAdapter.AdapterViewHolder>() {

    class AdapterViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        return AdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.today_list_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val currentItem = data?.get(position)
        holder.itemView.apply {
            val (a, b) = HomeFragment().convertTime(currentItem!!)

            today_subject_title_tv.text = currentItem.tTitle
            today_subject_time_tv.text = "$a-$b"
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

}