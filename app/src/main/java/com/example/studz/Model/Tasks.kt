package com.example.studz.Model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
class Tasks(
        val tkDescription: String,
        val tkDueDate: Timestamp,
        val tkTitle: String,
        val tkReminder: Boolean,
        val tkId: String
): Parcelable