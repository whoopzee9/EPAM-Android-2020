package com.university.epam_android_2020

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var textName: EditText
    private lateinit var textSecondName: EditText
    private lateinit var textEmail: EditText

    private lateinit var myDataBase: DatabaseReference;
    private val USER_KEY: String = "User"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        textName = findViewById(R.id.text_name)
        textSecondName = findViewById(R.id.text_second_name)
        textEmail = findViewById(R.id.text_email)
        myDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY)
    }

    fun onClickSaveButton(view: View) {
        val id: String? = myDataBase.key
        val name = textName.text.toString()
        val secName = textSecondName.text.toString()
        val email = textEmail.text.toString()
        val newUser = User(id, name, secName, email)
        if (name.isNotEmpty() && secName.isNotEmpty() && email.isNotEmpty()) {
            myDataBase.push().setValue(newUser)
            Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show()
            clearFields();
        } else {
            Toast.makeText(this, "Проверьте поля", Toast.LENGTH_SHORT).show()
        }

    }

    private fun clearFields() {
        textName.setText("")
        textSecondName.setText("")
        textEmail.setText("")
    }

    fun onClickReadButton(view: View) {

    }
}
