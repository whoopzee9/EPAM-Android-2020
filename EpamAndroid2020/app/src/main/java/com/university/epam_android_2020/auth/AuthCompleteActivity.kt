package com.university.epam_android_2020.auth

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.university.epam_android_2020.R
import com.university.epam_android_2020.firebaseDB.FirebaseDB
import com.university.epam_android_2020.user_data.Gps
import com.university.epam_android_2020.user_data.User

class AuthCompleteActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var myRef: DatabaseReference? = null
    private var user: FirebaseUser? = null
    private var etText: EditText? = null
    private var etTextField: EditText? = null
    private var etTextInput: EditText? = null

    var userData: User? = User()

    //test API
    private var mFirebaseDB = FirebaseDB()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_complete)

        myRef = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()
        user = mAuth?.currentUser

        etText = findViewById(R.id.et_text)
        etTextField = findViewById(R.id.et_field_text)
        etTextInput = findViewById(R.id.et_field_input)
        etText!!.setText(user!!.email)
    }

    fun onClickTest(view: View) {
        val userData = User(
            user!!.uid, "1Name", user!!.email, "http",
            Gps("23:30", 123.45, 34.56)
        )

        myRef!!.child("USERS").child(user!!.uid).setValue(userData)
        Toast.makeText(this, "Complete", Toast.LENGTH_SHORT).show()

    }

    fun onCLickAdd(view: View) {
        /*  myRef = FirebaseDatabase.getInstance().getReference("USERS/" + user!!.uid + "/gps")
          myRef!!.push().setValue(Gps("11;20", 34.44, 45.66))*/
        if (etTextField!!.text.isNotEmpty()) {
            mFirebaseDB.createData(etTextField!!.text.toString(), "test")
        }

    }

    fun onClickSignOut(view: View) {
        mAuth!!.signOut()
        finish()
    }

    fun onCLickRead(view: View) {
        val user: User? = mFirebaseDB.readUserData(user!!.uid)
        println("USER $user")

    }

    fun onCLickUpdate(view: View) {
        if (etTextField!!.text.isNotEmpty()) {
            mFirebaseDB.updateData(etTextField!!.text.toString(), "test")
        }

    }

    fun onCLickDelete(view: View) {}
    fun onClickAddGroup(view: View) {
        //mFirebaseDB.createGroup(etTextInput!!.text.toString())
        //mFirebaseDB.joinToGroup("ROOM6")
    }
    fun onClickReadSimple(view: View) {
      val x =  mFirebaseDB.simpleRead(user!!.uid, etTextField)
        println("INTO: $x")

       mFirebaseDB.simpleRead2(user!!.uid) { print(it)}
    }
}
