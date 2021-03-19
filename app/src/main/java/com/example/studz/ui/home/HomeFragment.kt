package com.example.studz.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.studz.Firestore.FirestoreClass
import com.example.studz.Fragment.ReminderDialogFragment
import com.example.studz.Fragment.TasksDialogFragment
import com.example.studz.Fragment.TodayDialogFragment
import com.example.studz.Model.Tasks
import com.example.studz.Model.Timetable
import com.example.studz.R
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment() {
    private lateinit var root:View
    private lateinit var dateString : String
    private val timeStampFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private val timeStampFormat2 = SimpleDateFormat("dd/MM h:mm a", Locale.getDefault())
    private val timeStampFormat3 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private lateinit var mAuth:FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var currentDate: Date
    private val dateFormat: DateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    private lateinit var tomorrowDate: LocalDate
    private var reminderList = mutableListOf<Tasks>()
    private lateinit var dateFormated: String

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_home, container, false)
        mAuth = FirebaseAuth.getInstance()
        user = mAuth.currentUser

        setDate()

        return root
    }

    private fun setDate(){
        currentDate = Date()
        dateString =timeStampFormat.format(currentDate)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            tomorrowDate = LocalDate.now().plusDays(1)

        }
        val sdf = SimpleDateFormat("EEE, MMM yyyy")
        dateFormated = sdf.format(currentDate)

        root.today_date_tv.text = dateFormated
        FirestoreClass().getTodayTimetable(this, currentDate, user.uid, tomorrowDate)
        FirestoreClass().getTask(this, currentDate, user.uid)
    }

    fun setupTodayUI(timetable: MutableList<Timetable>){
        if(timetable.size>0){
            subject2_title_tv.text = timetable[0].tTitle

            val (a, b) = convertTime(timetable[0])
            subject2_date_title_tv.text = "$a-$b"

            root.today_card.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelableArrayList("timetable", ArrayList(timetable))
                bundle.putString("date", dateFormated)
                val dialog = TodayDialogFragment()
                dialog.arguments = bundle
                dialog.show(requireFragmentManager(), "todayDialog")
            }
        }else{
            subject_layout_linear.visibility = View.GONE
            empty_title_tv.visibility = View.VISIBLE
        }
        if(timetable.size>1){
            subject1_title_tv.text = timetable[1].tTitle
            val (a, b) = convertTime(timetable[1])
            subject1_date_title_tv.text = "$a-$b"
        }
        if(timetable.size>2){
            subject3_title_tv.text = timetable[2].tTitle
            val (a, b) = convertTime(timetable[2])
            subject3_date_title_tv.text = "$a-$b"
        }
    }

    fun setupTaskUI(tasks: MutableList<Tasks>){
        if (tasks.size>0){
            task_date_title_tv.text = "// Tasks     "
            task1_tv.text = "- "+tasks[0].tkTitle
            task1_time_tv.text = timeStampFormat2.format(tasks[0].tkDueDate.toDate())
        }else{
            task_card.visibility = View.GONE
        }
        if (tasks.size>1){
            task2_tv.text = "- "+tasks[1].tkTitle
            task2_time_tv.text = timeStampFormat2.format(tasks[1].tkDueDate.toDate())
        }

        var count = 0
        for (reminder in tasks){
            if (reminder.tkReminder){
                val timeString = convertTime2(reminder, currentDate)
                count++
                reminderList.add(reminder)

                when(count){
                    1 -> {
                        reminder1_tv.text = "$timeString | ${reminder.tkTitle}"
                        reminder1_time_tv.text = dateFormat.format(reminder.tkDueDate.toDate())
                    }
                    2 -> {
                        reminder2_tv.text = "$timeString | ${reminder.tkTitle}"
                        reminder2_time_tv.text = dateFormat.format(reminder.tkDueDate.toDate())
                    }
                    3 -> {
                        reminder3_tv.text = "$timeString | ${reminder.tkTitle}"
                        reminder3_time_tv.text = dateFormat.format(reminder.tkDueDate.toDate())
                    }
                }
            }
        }
        if (count==0){
            Reminder_card.visibility = View.GONE
        }

        if (tasks.size>0){
            var taskCount = 0
            for (task in tasks) {
                val tkDueDate = task.tkDueDate.toDate()
                val date = timeStampFormat3.format(tkDueDate)
                if (date.equals(tomorrowDate.toString())) {
                    taskCount++
                }
            }
            tomorrow_NumTasks_tv.text = "$taskCount\nTasks"
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                tomorrow_date_tv.text = tomorrowDate.dayOfWeek.toString()
            }
        }

        root.task_card.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList("tasks", ArrayList(tasks))
            val dialog = TasksDialogFragment()
            dialog.arguments = bundle
            dialog.show(requireFragmentManager(), "tasksDialog")
        }

        root.Reminder_card.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList("reminder", ArrayList(reminderList))
            val dialog = ReminderDialogFragment()
            dialog.arguments = bundle
            dialog.show(requireFragmentManager(), "reminderDialog")
        }
    }

    fun setupTomorrowUI(timetableList2: MutableList<Timetable>) {
        var classNumber = 0
        var examNumber = 0
        if (timetableList2.size>0){
            for (item in timetableList2) {
                if (item.tCategory.equals("class")){
                    ++classNumber
                }
                if(item.tCategory.equals("exam")){
                    ++examNumber
                }
            }
            tomorrow_NumClass_tv.text = "$classNumber\nClasses"
            tomorrow_NumExams_tv.text = "$examNumber\nExams"
        }
    }

    fun convertTime(timetable: Timetable): Array<String> {
        val startTime = timetable.tTimeStart
        val endTime = timetable.tTimeEnd

        return arrayOf(startTime, endTime)
    }

    fun convertTime2(reminder: Tasks, date: Date): String {
        val timeLeft = reminder.tkDueDate.seconds - Timestamp(date).seconds
        val hours = TimeUnit.SECONDS.toHours(timeLeft)
        val minutes = TimeUnit.SECONDS.toMinutes(timeLeft) - TimeUnit.SECONDS.toHours(timeLeft) * 60

        var timeString = String.format("%2dhr%2dmins", hours, minutes)

        if (hours>24){
            val day = TimeUnit.SECONDS.toDays(timeLeft)
            val hour = hours - (day *24)
            timeString = " " + day.toString() + "day" + hour.toString() + "hr"
        }

        return timeString
    }

}