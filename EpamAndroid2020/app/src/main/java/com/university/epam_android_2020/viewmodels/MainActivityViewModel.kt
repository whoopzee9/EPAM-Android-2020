package com.university.epam_android_2020.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.university.epam_android_2020.repositories.GroupRepository
import com.university.epam_android_2020.user_data.CurrentGroup
import com.university.epam_android_2020.user_data.Group
import com.university.epam_android_2020.user_data.User
import javax.security.auth.callback.Callback

class MainActivityViewModel : ViewModel() {
    var group: MutableLiveData<Group> = MutableLiveData()
    var repository = GroupRepository.instance

    fun init() {
        //repository = GroupRepository.instance
        group = MutableLiveData()
        /*repository.getGroupFromDB {
            val currGroup = CurrentGroup.instance
            val tmp = Group(currGroup.groupName, it)
            group.postValue(tmp)
        }*/
        updateGroup()

    }

    fun getGroup(): LiveData<Group> {
        return group
    }

    fun updateGroup() {
        println("before updating group")
        repository.getGroupFromDB {
            println("updating group")
            val currGroup = CurrentGroup.instance
            val tmp = Group(currGroup.groupName, it)
            group.postValue(tmp)
        }
    }

    fun setGroup(data: Group) {
        group.value = data
    }

    //test
    fun listenChange(it: User?) {
        updateGroup()
    }
}