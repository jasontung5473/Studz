package com.example.studz.Firestore

import android.util.Log
import com.example.studz.Model.Constants
import com.example.studz.Model.Tasks
import com.example.studz.Model.Timetable
import com.example.studz.ui.home.HomeFragment
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


class FirestoreClass {
    private val mFirestore = FirebaseFirestore.getInstance()

    fun getTodayTimetable(activity: HomeFragment, currentDate: Date, uid: String, tomorrowDate: LocalDate){
        val timetableList = mutableListOf<Timetable>()
        val timetableList2 = mutableListOf<Timetable>()

        mFirestore.collection(Constants.TIMETABLE)
            .whereEqualTo(Constants.USER_ID, uid)
            //.whereGreaterThan(Constants.TIME_END,dayOfTheWeek)
            .orderBy(Constants.TIME_END, Query.Direction.ASCENDING)
            .get()
            .addOnCompleteListener{ task->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val mutableMap: MutableMap<String, Any> = document.data
                        val id = document.id

                        val tDayArray = mutableMap[Constants.TIME_DAY] as ArrayList<String>
                        val sdf = SimpleDateFormat("EEEE")
                        val dayOfTheWeek = sdf.format(currentDate)

                        for (item in tDayArray){
                            if (item.equals(dayOfTheWeek)){
                                val timetable = Timetable(
                                        mutableMap[Constants.TIME_START].toString(),
                                        mutableMap[Constants.TIME_END].toString(),
                                        mutableMap[Constants.TIME_TITLE].toString(),
                                        mutableMap[Constants.TIME_CATEGORY].toString(),
                                        mutableMap[Constants.TIME_DAY] as ArrayList<String>,
                                        id
                                )
                                timetableList.add(timetable)
                            }

                            val tomor_dow = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                tomorrowDate.dayOfWeek
                            } else {
                                TODO("VERSION.SDK_INT < O")
                            }
                            if (item.toUpperCase(Locale.getDefault()) == tomor_dow.toString()){
                                val timetable2 = Timetable(
                                        mutableMap[Constants.TIME_START].toString(),
                                        mutableMap[Constants.TIME_END].toString(),
                                        mutableMap[Constants.TIME_TITLE].toString(),
                                        mutableMap[Constants.TIME_CATEGORY].toString(),
                                        mutableMap[Constants.TIME_DAY] as ArrayList<String>,
                                        id
                                )
                                timetableList2.add(timetable2)
                            }
                        }
                    }
                    activity.setupTodayUI(timetableList)
                    activity.setupTomorrowUI(timetableList2)
                    Log.d("Get timetable", "onSuccess: timetable firebase get")
                }
            }.addOnFailureListener{ e->
                Log.e(
                        activity.javaClass.simpleName,
                        "Error in get data",
                        e
                )
            }

    }

    fun getTask(activity: HomeFragment, date: Date, uid: String){
        val taskList = mutableListOf<Tasks>()

        mFirestore.collection(Constants.TASKS)
                .whereEqualTo(Constants.USER_ID, uid)
                .whereGreaterThan(Constants.TASKS_DUE_DATE,date)
                .orderBy(Constants.TASKS_DUE_DATE, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener{ task->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            val mutableMap: MutableMap<String, Any> = document.data
                            val id = document.id

                            val tasks = Tasks(
                                    mutableMap[Constants.TKDESCRIPTION].toString(),
                                    mutableMap[Constants.TKDUEDATE] as Timestamp,
                                    mutableMap[Constants.TKTITLE].toString(),
                                    mutableMap[Constants.TKREMINDER] as Boolean,
                                    id
                            )
                            taskList.add(tasks)
                        }
                        activity.setupTaskUI(taskList)
                        Log.d("Get timetable", "onSuccess: tasks firebase get")
                    }
                }.addOnFailureListener{ e->
                    Log.e(
                            activity.javaClass.simpleName,
                            "Error in get data",
                            e
                    )
                }

    }

}