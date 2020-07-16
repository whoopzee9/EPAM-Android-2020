package com.university.epam_android_2020.user_data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: String? = "",
    var name: String? = "",
    var email: String? = "",
    var photo: String? = "",
    var gps: Gps = Gps("", 0.0, 0.0)
):Parcelable {

    override fun toString(): String {
        return "User(id=$id \n name=$name \n email=$email \n photo=$photo \n gps=$gps \n)"
    }
}