package com.example.titanapi

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.example.titanapi.controllers.RequestImage
import com.example.titanapi.controllers.RequestLogin
import com.example.titanapi.data.database.AppDatabase
import com.example.titanapi.views.TitanMobApp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var userLocation: String = ""

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "dw"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TitanMobApp()
        }

        getLastLocation()
        RequestLogin.setDatabase(db)
        RequestImage.setDatabase(db)
    }

    private fun getLastLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
                    }
                    else {
                        val lastLocation = fusedLocationClient.lastLocation

                        lastLocation.addOnSuccessListener {
                            if (it != null) {
                                userLocation =
                                    if (isLocationInPoland(applicationContext, it.latitude, it.longitude)) {
                                        "PL"
                                    } else {
                                        "X"
                                    }
                            }
                        }
                    }
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    Log.d("permit", "coarse")
                }
                else -> {
                    finishAndRemoveTask()
                }
            }
        }

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    private fun isLocationInPoland(context: Context, latitude: Double, longitude: Double): Boolean {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)

        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                val country = addresses[0].countryCode
                return country == "PL"
            }
        }
        return false
    }

}
