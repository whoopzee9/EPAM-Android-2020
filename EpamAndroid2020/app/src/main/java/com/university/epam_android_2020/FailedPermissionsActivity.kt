package com.university.epam_android_2020

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.university.epam_android_2020.services.ForegroundService
import kotlinx.android.synthetic.main.activity_failed_permissions.*

class FailedPermissionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_failed_permissions)
        BAskAgain.setOnClickListener {
            checkPermissions()
        }
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), 100)
        } else {
            val serviceIntent = Intent(this, ForegroundService::class.java)
            serviceIntent.putExtra("inputExtra", "GPS usage in foreground")
            ContextCompat.startForegroundService(this, serviceIntent)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        println("request code = ${requestCode}, ${grantResults[0]}, ${permissions[0]},${permissions[1]}")
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            checkPermissions()
        } else {
            val toast: Toast = Toast.makeText(applicationContext, "No no no no permissions! ${grantResults[0]}", Toast.LENGTH_LONG)
            toast.show()
        }
    }

}