package com.university.epam_android_2020.repositories

import androidx.lifecycle.MutableLiveData
import com.university.epam_android_2020.models.MapOfUsers

class GroupRepository {
    var instance:GroupRepository? = null
        get() {
        if (field == null) {
            field = GroupRepository()
        }
        return field
    }

    fun getGroupFromDB():MutableLiveData<MapOfUsers> {
        //TODO somehow retrieve data from DB
        val data:MutableLiveData<MapOfUsers> = MutableLiveData()
        return data
    }
}