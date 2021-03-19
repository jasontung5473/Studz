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
import com.example.studz.Adapter.TasksListAdapter
import com.example.studz.Model.Tasks
import com.example.studz.R
import kotlinx.android.synthetic.main.fragment_tasks_dialog.view.*

class TasksDialogFragment: DialogFragment() {
    private lateinit var root:View
    private lateinit var adapter: TasksListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_tasks_dialog, container, false)
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        val tasks = requireArguments().getParcelableArrayList<Tasks>("tasks")

        adapter = TasksListAdapter(tasks)
        root.tasks_dialog_recyclerview.adapter = adapter
        root.tasks_dialog_recyclerview.layoutManager = LinearLayoutManager(context)
        root.tasks_dialog_recyclerview.hasFixedSize()
        return root
    }
}