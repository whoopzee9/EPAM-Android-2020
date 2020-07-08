package com.university.epam_android_2020.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
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
        // etRegPassword = findViewById(R.id.et_reg_password)
        etRegPassword = findViewById<View>(R.id.et_reg_password) as EditText
        etRegConfPassword = findViewById(R.id.et_password_confirm)

        mAuth = FirebaseAuth.getInstance()

    }

    fun onClickSubmit(view: View) {
        if (etName?.text!!.isNotEmpty()
            && etRegEmail?.text!!.isNotEmpty()
            && etRegPassword?.text!!.isNotEmpty()
            && etRegConfPassword?.text!!.isNotEmpty()
            && (etRegPassword?.text!!.toString().equals(etRegConfPassword?.text!!.toString()))
        ) {
            val email = etRegEmail!!.text.toString()
            val password = etRegEmail!!.text.toString()
            mAuth!!
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth!!.currentUser
                        val userData = User(
                            user!!.uid, etName!!.text.toString(), user.email, "http",
                            Gps("${Calendar.getInstance().time}", 0.0, 0.0)
                        )
                        mFirebaseDB.createUser(userData)
                        Toast.makeText(this, "Success registration", Toast.LENGTH_SHORT).show()
                        sendEmailVerification();
                        finish()
                    } else {
                        Toast.makeText(this, "Authentication failed!", Toast.LENGTH_SHORT).show()
                    }
                }

        } else {
            Toast.makeText(this, "Check the fields", Toast.LENGTH_SHORT).show()
        }
    }
    private fun sendEmailVerification() {
        val user = mAuth!!.currentUser
        user!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Verification email sent to " + user.email!!,
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
