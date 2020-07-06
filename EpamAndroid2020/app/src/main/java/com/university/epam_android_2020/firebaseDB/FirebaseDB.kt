package com.university.epam_android_2020.firebaseDB

import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.university.epam_android_2020.user_data.Gps
import com.university.epam_android_2020.user_data.User
import java.util.*

class FirebaseDB : ExtensionsCRUD {
    private var dataRef = FirebaseDatabase.getInstance().reference
    private var groupsRef = FirebaseDatabase.getInstance().getReference("GROUP")
    private var mAuth = FirebaseAuth.getInstance()
    private var user = mAuth.currentUser
    var value: String? = "WORK??"
    private var userData: User? = User()
    fun reference(): DatabaseReference {
        return dataRef
    }

    override fun setAuth(): FirebaseAuth? {
        TODO("Not yet implemented")
    }

    override fun createPath(path: String) {
        //empl EPAM:/{path}
        dataRef.child(path).setValue(true)
    }

    override fun createGroup(groupName: String) {
        groupsRef.child(groupName).child("admin").setValue("admin")
    }

    override fun joinToGroup(groupName: String) {
        print("user" + user!!.uid)
        groupsRef.child(groupName).child(user!!.uid).setValue(1)
    }

    override fun createUser(path: String, user: User) {
        dataRef.child(path).setValue(user)
    }

    override fun setLocation(longitude: Double, latitude: Double) {
        dataRef.child("USERS/").child(user!!.uid).child("gps")
            .setValue(
                Gps(
                    time = "${Calendar.getInstance().time}",
                    longitude = longitude,
                    latitude = latitude
                )
            )
    }

    override fun getLocation(): String? {
/*        var location: Gps? = null
        dataRef.child("USERS/").child(user!!.uid).child("gps")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val gpsData = dataSnapshot.getValue(Gps::class.java)
                    location = gpsData
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })
        return location*/
        var location: String? = ""
        dataRef.child("USERS/").child(user!!.uid).child("gps").child("latitude")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val gpsData = dataSnapshot.getValue(Double::class.java)
                    location = gpsData.toString()
                    println("LOc IN FUNc $location")
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })
        return location
    }

    override fun createData(value: String?, path: String) {

    }

    override fun readUserData(path: String): User? {

        dataRef.child("USERS/${path}/").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userData = dataSnapshot.getValue(User::class.java)
                println("??????$userData")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
        return userData
    }

    override fun updateData(value: String?, path: String) {
        dataRef.child(path).setValue(value)
    }

    override fun deleteData(value: String?, path: String) {
        TODO("Not yet implemented")
    }


    fun simpleRead(path: String, etField: EditText?): String? {
        var data: String? = "((("
        dataRef.child("USERS/${path}/email/")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    data = dataSnapshot.getValue(String::class.java)
                    etField!!.setText(data)
                    println("FROM FUNC SIMPLE: $data")
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })
        return data
    }

    fun simpleRead2(path: String, callBack: (Double?) -> Unit) {
        var data: Double? = 0.0
        dataRef.child("USERS/${path}/gps/latitude")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    data = dataSnapshot.getValue(Double::class.java)
                    callBack(data)
                    println("FROM FUNC SIMPLE2: $data")
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })
    }
}