package com.university.epam_android_2020.firebaseDB

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.university.epam_android_2020.user_data.Gps
import com.university.epam_android_2020.user_data.User
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class FirebaseDB : ExtensionsCRUD {
    //Reference
    private var dataRef = FirebaseDatabase.getInstance().reference
    private var groupsRef = FirebaseDatabase.getInstance().getReference("GROUP")
    private var usersRef = FirebaseDatabase.getInstance().getReference("USERS")
    private var storageRef = FirebaseStorage.getInstance().reference

    //Auth
    private var mAuth = FirebaseAuth.getInstance()

    //Authenticated user
    var user = mAuth.currentUser

    /**
     * Creates a new path from string with val true.
     *
     * @param path Name of end future path .
     */
    override fun createPath(path: String) {
        //exmpl EPAM:/{path}
        dataRef.child(path).setValue(true)
    }

    /**
     * Creates a new group from name.
     *
     * @param groupName Name of the future group.
     */
    override fun createGroup(groupName: String) {
        if (groupName.isNotEmpty()) {
            groupsRef.child(groupName).child(user!!.uid).setValue(user!!.uid)
        }
    }

    /**
     * Enables adding a user to the group.
     *
     * @param groupName Name of the group.
     */
    override fun joinToGroup(groupName: String) {
        if (groupName.isNotEmpty()) {
            groupsRef.child(groupName).child(user!!.uid).setValue(user!!.uid)
        }
    }


    /**
     * Gets all groups
     *
     * @param callBack Returned callback param of type MutableList<String?>
     * @return callback Return class MutableList<String?> with list of groups.
     */
    override fun getAllGroups(callBack: (MutableList<String?>) -> Unit) {
        val grList: MutableList<String?> = mutableListOf()
        groupsRef
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (item in snapshot.children) {
                            grList.add(item.key)
                        }
                        callBack(grList)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })
    }

    /**
     * Adds a new user to the database.
     *
     * @param userData User data of the "User" type.
     */
    override fun createUser(userData: User) {
        usersRef.child(user!!.uid).setValue(userData)
    }

    /**
     * Set(Update) coordinates of user. The time is generated automatically.
     *
     * @param longitude Double type value.
     * @param latitude Double type value.
     */
    override fun setLocation(longitude: Double, latitude: Double) {
        usersRef.child(user!!.uid).child("gps")
            .setValue(
                Gps(
                    time = "${Calendar.getInstance().time}",
                    longitude = longitude,
                    latitude = latitude
                )
            )
    }

    /**
     * Get coordinates of user. Callback return class Gps.
     *
     * @param callBack Returned callback param of type Gps
     * @return callback Return class Gps with gps info.
     */
    override fun getLocation(callBack: (Gps?) -> Unit) {
        usersRef.child(user!!.uid).child("gps")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val gpsData = dataSnapshot.getValue(Gps::class.java)
                    if (gpsData != null) {
                        callBack(gpsData)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })
    }

    /**
     * Creates a new value to the path
     *
     * @param value the value of created data.
     * @property path The relative path from start reference to the new one that should be created.
     */
    override fun createData(value: String?, path: String) {
        dataRef.child(path).setValue(value)
    }

    /**
     * Get data of user. Callback return class User.
     *
     * @param callBack Returned callback param of type User
     * @return callback Return class User with user info.
     */
    override fun getUserData(callBack: (User?) -> Unit) {
        dataRef.child("USERS/${user!!.uid}/")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    callBack(user)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })
    }

    /**
     * Get data of all users. Callback return MutableList of User.
     *
     * @param callBack Returned callback param of type MutableList<User?>
     * @return callback Return class MutableList<User?> with list of user.
     */
    override fun getAllUsers(callBack: (MutableList<User>) -> Unit) {
        var data: User?
        val userData: MutableList<User> = mutableListOf()
        usersRef
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val children = snapshot.children
                        for (item in children) {
                            val retrieveUser = item.getValue(User::class.java)
                            if (retrieveUser != null) {
                                userData.add(retrieveUser)
                            }
                        }
                        callBack(userData)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })
    }

    //TODO may be delete
    override fun updateData(value: String?, path: String) {
        dataRef.child(path).setValue(value)

    }

    /**
     * Delete data of user.
     *
     * @param userPath Deletes a user along its path. Also deletes from groups
     *
     */
    override fun deleteUserData(userPath: String) {
        usersRef.child(userPath).removeValue()

    }


    /**
     * Delete user from group.
     *
     * @param groupName Deletes a user from group along its path.
     *
     */
    override fun deleteFromGroup(groupName: String?) {
        if (groupName != null) {
            if (groupName.isNotEmpty()) {
                groupsRef.child(groupName).child(user!!.uid).removeValue()
            }
        }
    }

    /**
     * Delete user from all groups.
     *
     * @param userID Deletes user from all groups.
     *
     */
    override fun deleteFromAllGroups(userID: String) {
        groupsRef
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (item in snapshot.children) {
                            val groupMap = item.value as HashMap<*, *> //pizdec

                            if (groupMap.keys.contains(userID)) {
                                println("?? ${item.key}")
                                deleteFromGroup(item!!.key)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })
    }

    // TODO MAKE IT EASIER
    /**
     * Get data of all users in group. Callback return MutableList of User.
     *
     * @param callBack Returned callback param of type MutableList<User?>
     * @return callback Return class MutableList<User?> with list of user.
     */
    override fun getUsersFromGroup(groupName: String, callBack: (MutableList<User?>) -> Unit) {
        if (groupName.isNotEmpty()) {

            val userData: MutableList<User?> = mutableListOf()
            groupsRef.child(groupName)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            var count = 0L;
                            val maxItems = snapshot.childrenCount
                            for (item in snapshot.children) {
                                dataRef.child("USERS/${item.key}/")
                                    .addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                                            val user = dataSnapshot.getValue(User::class.java)
                                            // println("USER In FUNC: $user")
                                            userData.add(user)
                                            count++
                                            if (maxItems == count) {
                                                callBack(userData)
                                            }
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                            // Failed to read value
                                        }
                                    })
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                    }
                })
        }
    }

    override fun getListUsersFromGroup(groupName: String, callBack: (ArrayList<String?>) -> Unit) {
        val usersList: ArrayList<String?> = arrayListOf()
        groupsRef.child(groupName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (item in snapshot.children) {
                            usersList.add(item.key)
                        }
                        println("IDDDDD $usersList")
                        callBack(usersList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })
    }

    /**
     * Adds a new user to the database.
     *
     * @param userData User data of the "User" type.
     */
    fun createUserFromReg(uid: String, userData: User) {
        usersRef.child(uid).setValue(userData)

    }

    //TODO fix and set description
    override fun listenChange(users: ArrayList<String?>, callBack: ((User?) -> Unit)) {
        val messageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val message = dataSnapshot.getValue(User::class.java)
                    callBack(message)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }

        // id users
        //var listUserPath = listOf(user!!.uid, "0MdOWT14PrP6eESIOtqOQ93REA62")
        var listUserPath = users
        for (item in listUserPath) {
            if (item != null) {
                usersRef.child(item).addValueEventListener(messageListener)
            }
        }

    }

    fun getStorageRef() : StorageReference {
        return storageRef
    }

    fun getUsersRef() : DatabaseReference {
        return usersRef
    }
}