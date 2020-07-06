package com.university.epam_android_2020.gps

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

class MyLocationListener :LocationListener {
    var locationListenerInterface: LocationListenerInterface? = null

    override fun onLocationChanged(location: Location?) {
        println("location changed!")
        locationListenerInterface?.onLocationChanged(location)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        println("status changed!")
    }

    override fun onProviderEnabled(provider: String?) {
        println("provider enabled!")
    }

    override fun onProviderDisabled(provider: String?) {
        println("provider disabled!")
    }
}