package com.university.epam_android_2020.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.university.epam_android_2020.models.MapOfUsers
import com.university.epam_android_2020.repositories.GroupRepository

class MainActivityViewModel:ViewModel() {
    var group:MutableLiveData<MapOfUsers> = MutableLiveData()
    lateinit var repository:GroupRepository

    fun init() {
        if(group != null){
            return;
        }
        repository = GroupRepository().instance!!
        group = repository.getGroupFromDB()
    }

    fun getGroup():LiveData<MapOfUsers> {
        return group
    }

    fun setGroup(data: MapOfUsers) {
        group.value = data
    }

}