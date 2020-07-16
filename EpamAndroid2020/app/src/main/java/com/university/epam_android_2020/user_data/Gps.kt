package com.university.epam_android_2020.user_data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Gps(
    var time: String = "",
    var longitude: Double = 0.0,
    var latitude: Double = 0.0
) :Parcelable {
    override fun toString(): String {
        return "Gps(time='$time', longitude=$longitude, latitude=$latitude)"
    }
}