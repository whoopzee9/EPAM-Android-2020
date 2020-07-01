package com.university.epam_android_2020.repositories

import androidx.lifecycle.MutableLiveData
import com.university.epam_android_2020.models.User

class GroupRepository {
    var instance:GroupRepository? = null
        get() {
        if (instance == null) {
            instance = GroupRepository()
        }
        return instance
    }

    fun getGroupFromDB():MutableLiveData<List<User>> {
        //TODO somehow retrieve data from DB
        val data:MutableLiveData<List<User>> = MutableLiveData()
        return data
    }
}