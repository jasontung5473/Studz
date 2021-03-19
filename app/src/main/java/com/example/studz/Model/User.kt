package com.example.studz.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
        val uName: String = "",
        val uPhoto: String = "",
        val uEmail: String = ""
): Parcelable
