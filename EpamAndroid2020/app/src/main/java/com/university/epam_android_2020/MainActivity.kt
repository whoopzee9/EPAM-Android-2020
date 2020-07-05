package com.university.epam_android_2020

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.university.epam_android_2020.models.MapOfUsers
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_USER_MAP = "EXTRA_USER_MAP"

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var map: MapOfUsers

    //redo
    companion object{
        private var isFirstStart = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isFirstStart) {
            isFirstStart = false
            val intent = Intent(this@MainActivity, GroupActivity::class.java)
            startActivity(intent)
        } else {
            map = intent.getSerializableExtra(EXTRA_USER_MAP) as MapOfUsers

            supportActionBar?.title = map.name

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            val mapFragment =
                supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)

            menuButton.setOnClickListener {
                val intent = Intent(this@MainActivity, GroupActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val boundsBuilder = LatLngBounds.Builder()
        for (place in map.places) {
            val latLng = LatLng(place.latitude, place.longitude)
            boundsBuilder.include(latLng)
            mMap.addMarker(MarkerOptions().position(latLng).title(place.title))
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 1000, 1000, 0))
    }
}
