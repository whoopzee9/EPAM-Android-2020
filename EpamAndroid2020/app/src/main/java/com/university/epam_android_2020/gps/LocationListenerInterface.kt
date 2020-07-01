package com.university.epam_android_2020.gps

import android.location.Location

interface LocationListenerInterface {
    fun onLocationChanged(location: Location?)
}