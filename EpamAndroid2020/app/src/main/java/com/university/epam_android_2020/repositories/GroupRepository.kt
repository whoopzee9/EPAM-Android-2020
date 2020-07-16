package com.university.epam_android_2020.repositories

import com.university.epam_android_2020.firebaseDB.FirebaseDB
import com.university.epam_android_2020.user_data.CurrentGroup
import com.university.epam_android_2020.user_data.User

class GroupRepository {
    companion object {
        var instance = GroupRepository()
    }

    var mFirebaseDB = FirebaseDB()

    fun getGroupFromDB(callBack: (MutableList<User?>) -> Unit) {
        //TODO somehow retrieve data from DB
        val current = CurrentGroup.instance
        if (current.groupName.isNotEmpty()) {
            mFirebaseDB.getUsersFromGroup(current.groupName, callBack)
        }
    }
}