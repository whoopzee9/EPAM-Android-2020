package com.university.epam_android_2020.user_data

data class User(
    var id: String? = "",
    var name: String? = "",
    var email: String? = "",
    var photo: String? = "",
    var gps: Gps = Gps("", 0.0, 0.0)
) {

    override fun toString(): String {
        return "User(id=$id, name=$name, email=$email, photo=$photo, gps=$gps)"
    }
}