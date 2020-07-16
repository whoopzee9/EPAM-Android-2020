package com.university.epam_android_2020.firebaseDB

import android.net.Uri
import com.university.epam_android_2020.user_data.Gps
import com.university.epam_android_2020.user_data.User

interface ExtensionsCRUD {

    fun setLocation(longitude: Double, latitude: Double)
    fun getLocation(callBack: (Gps?) -> Unit)

    fun createPath(path: String)
    fun createGroup(groupName: String)
    fun joinToGroup(groupName: String)
    fun getAllGroups(callBack: (MutableList<String?>) -> Unit)

    fun createData(value: String?, path: String)

    fun createUser(userData: User)
    fun getUserData(callBack: (User?) -> Unit)
    fun getAllUsers(callBack: (MutableList<User>) -> Unit)
    fun deleteUserData(userPath: String)

    fun updateData(value: String?, path: String)

    fun deleteFromGroup(groupName: String?)
    fun deleteFromAllGroups(userID: String)
    fun getUsersFromGroup(groupName: String, callBack: (MutableList<User?>) -> Unit)
    fun getListUsersFromGroup(groupName: String, callBack: (ArrayList<String?>) -> Unit)

    fun listenChange(users: ArrayList<String?>, callBack: ((User?) -> Unit))

    fun putPhoto(uri: Uri, userID: String)
    fun setPhoto(userID: String)
    fun getUrlDefaultPhoto() : String
}