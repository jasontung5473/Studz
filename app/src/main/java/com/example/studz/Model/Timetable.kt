package com.example.studz.Model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
class Timetable(
    val tTimeStart: String,
    val tTimeEnd: String,
    val tTitle: String,
    val tCategory: String,
    val tDay: ArrayList<String>,
    var tId: String
): Parcelable