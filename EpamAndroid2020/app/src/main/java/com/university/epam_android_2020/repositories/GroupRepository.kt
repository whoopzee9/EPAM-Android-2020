package com.university.epam_android_2020.repositories

import androidx.lifecycle.MutableLiveData
import com.university.epam_android_2020.firebaseDB.FirebaseDB
import com.university.epam_android_2020.user_data.CurrentGroup
import com.university.epam_android_2020.user_data.Group
import com.university.epam_android_2020.user_data.User

class GroupRepository {
    companion object {
        var instance = GroupRepository()
    }

    var mFirebaseDB = FirebaseDB()

    fun getGroupFromDB(callBack: (MutableList<User?>) -> Unit)/*:MutableLiveData<Group>*/ {
        //TODO somehow retrieve data from DB
        val current = CurrentGroup.instance
        if (current.groupName.isNotEmpty()) {
            mFirebaseDB.getUsersFromGroup(current.groupName, callBack)
        }
    }
}