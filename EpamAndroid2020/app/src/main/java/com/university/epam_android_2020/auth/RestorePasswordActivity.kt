package com.university.epam_android_2020.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.university.epam_android_2020.R

class RestorePasswordActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var etResetEmail: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore_password)
        init()
    }

    private fun init() {
        mAuth = FirebaseAuth.getInstance()
        etResetEmail = findViewById(R.id.et_restore_email)
    }

    fun onClickSubmitRestore(view: View) {
        val email = etResetEmail!!.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Enter your email!", Toast.LENGTH_SHORT).show()
        } else {
            mAuth!!.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Check email to reset your password!",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Fail to send reset password email!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

}