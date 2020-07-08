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
    //private var mAuth: FirebaseAuth? = null
    private lateinit var mAuth: FirebaseAuth

    private var etEmail: EditText? = null;
    private var etPassword: EditText? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        mAuth = FirebaseAuth.getInstance();
        init()
    }

    override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        if (currentUser != null) {
           // val intent = Intent(this, GroupActivity::class.java)
            val intent = Intent(this, GroupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun init() {
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
    }

    fun onClickSignIn(view: View) {
        signIn(etEmail!!.text.toString().trim(), etPassword!!.text.toString().trim())
        //println("Loggggggg" + etEmail!!.text.toString() + " " + etPassword!!.text.toString())
        // signIn("daniilxt.dev@gmail.ru", "1234")
    }

    private fun signIn(email: String, password: String) {
        if (etEmail!!.text.toString().isNotEmpty()) {
            println("Loggggggg $email $password")

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, " Good!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, GroupActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Auth failed!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        } else {
            Toast.makeText(this, "Error Auth", Toast.LENGTH_SHORT).show()
        }
    }


    fun onClickRegister(view: View) {
        val intent = Intent(this, Registration::class.java)
        startActivity(intent)
    }

    fun onClickTest(view: View) {
        val user: FirebaseUser? = mAuth.currentUser
        Toast.makeText(this, "Email is: ${user?.email}", Toast.LENGTH_SHORT).show()
    }

    fun onClickRestore(view: View) {
        val intent = Intent(this, RestorePasswordActivity::class.java)
        startActivity(intent)
    }
}