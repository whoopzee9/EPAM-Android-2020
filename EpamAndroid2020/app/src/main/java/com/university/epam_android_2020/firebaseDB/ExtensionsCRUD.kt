package com.university.epam_android_2020.firebaseDB

import com.google.firebase.auth.FirebaseAuth
import com.university.epam_android_2020.user_data.User

interface ExtensionsCRUD {
    fun setAuth(): FirebaseAuth?;

    fun createData(value: String?, path: String)

    fun createUser(path: String, user: User)

    fun setLocation(longitude:Double, latitude:Double)
    fun getLocation():String?

    //CRUD
    fun createPath(path: String)
    fun createGroup(groupName: String)

    fun joinToGroup(groupName: String)

    fun readUserData(path: String): User?

    fun updateData(value: String?, path: String)

    fun deleteData(value: String?, path: String)

}