package com.university.epam_android_2020.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.core.content.ContextCompat

class ActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        println("action receiver!!")
        val serviceIntent = Intent(context, ForegroundService::class.java)
        context?.stopService(serviceIntent)
    }
}