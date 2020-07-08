package com.university.epam_android_2020.services

import android.Manifest
import android.R
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.NotificationCompat
import com.university.epam_android_2020.firebaseDB.FirebaseDB
import com.university.epam_android_2020.gps.LocationListenerInterface
import com.university.epam_android_2020.gps.MyLocationListener
import com.university.epam_android_2020.MainActivity as MainActivity


class ForegroundService: Service(), LocationListenerInterface {

    private val CHANNEL_ID = "ForegroundServiceChannel"
    private lateinit var locationManager: LocationManager
    private val myLocationListener: MyLocationListener = MyLocationListener()
    private val mFirebaseDB = FirebaseDB()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent!!.getStringExtra("inputExtra")
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        myLocationListener.locationListenerInterface = this
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText(input)
            //.setSmallIcon(R.drawable.ic_stat_name)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)

        //do heavy work on a background thread
        //stopSelf();

        println("started!! ${ActivityCompat.checkSelfPermission(applicationContext,
            Manifest.permission.ACCESS_COARSE_LOCATION)}, ${ActivityCompat.checkSelfPermission(applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION)}")

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            val toast: Toast = Toast.makeText(applicationContext, "No permissions(foreground fine)!", Toast.LENGTH_LONG)
            toast.show()
        }

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            val toast: Toast = Toast.makeText(applicationContext, "No permissions(foreground coarse)!", Toast.LENGTH_LONG)
            toast.show()
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0F, myLocationListener)
        } else {
            val toast: Toast = Toast.makeText(applicationContext, "No permissions(foreground)!", Toast.LENGTH_LONG)
            toast.show()
        }



        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onLocationChanged(location: Location?) {
        println("location changed to ${location!!.latitude} , ${location.longitude}")
        mFirebaseDB.setLocation(location!!.longitude, location.latitude)
    }
}