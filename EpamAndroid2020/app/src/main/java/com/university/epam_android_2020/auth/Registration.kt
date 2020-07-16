package com.university.epam_android_2020.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.university.epam_android_2020.GroupActivity
import com.university.epam_android_2020.R
import com.university.epam_android_2020.firebaseDB.FirebaseDB
import com.university.epam_android_2020.user_data.Gps
import com.university.epam_android_2020.user_data.User
import java.util.*

class Registration : AppCompatActivity() {
    private var etRegEmail: EditText? = null
    private var etName: EditText? = null
    private var etRegPassword: EditText? = null
    private var etRegConfPassword: EditText? = null

    //Firebase references
    private var mAuth: FirebaseAuth? = null
    private val mFirebaseDB = FirebaseDB()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        init()
    }

    private fun init() {
        etName = findViewById(R.id.et_reg_name)
        etRegEmail = findViewById(R.id.et_restore_email)
        etRegPassword = findViewById(R.id.et_reg_password)
        etRegConfPassword = findViewById(R.id.et_password_confirm)

        mAuth = FirebaseAuth.getInstance()
    }

    fun onClickSubmit(view: View) {
        if (etName?.text!!.isNotEmpty()
            && etRegEmail?.text!!.isNotEmpty()
            && etRegPassword?.text!!.isNotEmpty()
            && etRegConfPassword?.text!!.isNotEmpty()
            && (etRegPassword?.text!!.toString() == etRegConfPassword?.text!!.toString())
        ) {
            mAuth!!
                .createUserWithEmailAndPassword(etRegEmail!!.text.toString(), etRegEmail!!.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        mAuth = FirebaseAuth.getInstance()
                        val user = mAuth!!.currentUser
                        println("??? ${user!!.uid}")
                        val userData = User(
                            user.uid,
                            etName!!.text.toString(),
                            etRegEmail!!.text.toString(),
                            "http",
                            Gps("${Calendar.getInstance().time}", 0.0, 0.0)
                        )
                        mFirebaseDB.getStorageRef().child("default_user_icon.jpg")
                            .downloadUrl
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val defaultUrl = it.result.toString()
                                    mFirebaseDB.getUsersRef().child(user.uid).child("photo")
                                        .setValue(defaultUrl)
                                }
                            }
                        mFirebaseDB.createUserFromReg(user.uid, userData)
                        Toast.makeText(this, "Success registration!", Toast.LENGTH_SHORT).show()
                        sendEmailVerification();
                        startActivity(Intent(this,GroupActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Authentication failed!", Toast.LENGTH_SHORT).show()
                    }
                }

        } else {
            Toast.makeText(this, "Check the fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendEmailVerification() {
        val user = mAuth!!.currentUser
        user!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Verification email sent to ${user.email}!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
