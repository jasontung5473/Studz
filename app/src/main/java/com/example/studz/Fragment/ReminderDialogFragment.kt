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
import com.example.studz.Adapter.ReminderListAdapter
import com.example.studz.Model.Tasks
import com.example.studz.R
import kotlinx.android.synthetic.main.fragment_reminder_dialog.view.*


class ReminderDialogFragment: DialogFragment() {
    private lateinit var root:View
    private lateinit var adapter: ReminderListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_reminder_dialog, container, false)
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        val reminder = requireArguments().getParcelableArrayList<Tasks>("reminder")

        adapter = ReminderListAdapter(reminder)
        root.reminder_dialog_recyclerview.adapter = adapter
        root.reminder_dialog_recyclerview.layoutManager = LinearLayoutManager(context)
        root.reminder_dialog_recyclerview.hasFixedSize()
        return root
    }
}