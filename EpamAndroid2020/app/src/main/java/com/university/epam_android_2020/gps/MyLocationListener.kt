package com.university.epam_android_2020.gps

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

class MyLocationListener :LocationListener {
    private var locationListenerInterface: LocationListenerInterface? = null

    override fun onLocationChanged(location: Location?) {
        locationListenerInterface?.onLocationChanged(location)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("Not yet implemented")
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("Not yet implemented")
    }
}