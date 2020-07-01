package com.university.epam_android_2020

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.university.epam_android_2020.models.User
import com.university.epam_android_2020.viewmodels.MainActivityViewModel
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private lateinit var textName: EditText
    private lateinit var textSecondName: EditText
    private lateinit var textEmail: EditText

    private lateinit var myDataBase: DatabaseReference;
    private val USER_KEY: String = "User"
    private val mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

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

        mainActivityViewModel.init()

        mainActivityViewModel.getGroup().observe(this, Observer {  }) //TODO Observe
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION), 100)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun onClickSaveButton(view: View) {
        val id: String? = myDataBase.key
        val name = textName.text.toString()
        val secName = textSecondName.text.toString()
        val email = textEmail.text.toString()
        val newUser = User(
            id,
            name,
            secName,
            email
        )
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
