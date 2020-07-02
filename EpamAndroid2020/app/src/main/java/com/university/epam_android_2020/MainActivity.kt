package com.university.epam_android_2020

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.university.epam_android_2020.models.User
import com.university.epam_android_2020.services.ForegroundService
import com.university.epam_android_2020.viewmodels.MainActivityViewModel


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
        } else {
            startService()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 100 && grantResults[0] == Activity.RESULT_OK) {
            checkPermissions()
        } else {
            val toast: Toast = Toast.makeText(applicationContext, "No permissions!", Toast.LENGTH_LONG)
            toast.show()
        }
    }

    fun startService() {
        val serviceIntent = Intent(this, ForegroundService::class.java)
        serviceIntent.putExtra("inputExtra", "GPS usage in foreground")
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    fun stopService() {
        val serviceIntent = Intent(this, ForegroundService::class.java)
        stopService(serviceIntent)
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
