package com.university.epam_android_2020.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.university.epam_android_2020.GroupActivity
import com.university.epam_android_2020.R

class AuthActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    private var etEmail: EditText? = null;
    private var etPassword: EditText? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        init()
    }

    private fun init() {
        mAuth = FirebaseAuth.getInstance()

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
    }

    fun onClickSignIn(view: View) {
        if (etEmail != null && etPassword != null) {
            signIn(etEmail?.text.toString().trim(), etPassword?.text.toString().trim())
        }
    }

    private fun signIn(email: String, password: String) {
        if (email.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, " Welcome back!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, GroupActivity::class.java))
//                        startActivity(Intent(this, AuthCompleteActivity::class.java))
                    } else {
                        Toast.makeText(this, "Auth failed!", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Error Auth.", Toast.LENGTH_SHORT).show()
        }
    }


    fun onClickRegister(view: View) {
        startActivity(Intent(this, Registration::class.java))
    }

    fun onClickRestore(view: View) {
        startActivity(Intent(this, RestorePasswordActivity::class.java))
    }
}