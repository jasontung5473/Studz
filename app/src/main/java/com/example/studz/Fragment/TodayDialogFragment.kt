package com.example.studz.Fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studz.Adapter.TodayListAdapter
import com.example.studz.Model.Timetable
import com.example.studz.R
import kotlinx.android.synthetic.main.fragment_today_dialog.view.*

class TodayDialogFragment: DialogFragment() {
    private lateinit var root:View
    private lateinit var adapter: TodayListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_today_dialog, container, false)
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        root.today_dialog_date_tv.text = requireArguments().getString("date")
        val timetable = requireArguments().getParcelableArrayList<Timetable>("timetable")

        adapter = TodayListAdapter(timetable)
        root.today_dialog_recyclerview.adapter = adapter
        root.today_dialog_recyclerview.layoutManager = LinearLayoutManager(context)
        root.today_dialog_recyclerview.hasFixedSize()
        return root
    }
}