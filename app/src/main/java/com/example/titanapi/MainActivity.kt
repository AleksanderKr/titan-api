package com.example.titanapi

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import com.example.titanapi.views.CameraView
import com.example.titanapi.views.TitanMobApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TitanMobApp()
            //CameraView()
        }

    }

    private fun hasPermissions(): Boolean {
        return CAMERAX_PERMITS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
    companion object {
        private val CAMERAX_PERMITS = arrayOf(
            Manifest.permission.CAMERA
        )
    }
}