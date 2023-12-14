package com.example.titanapi

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.titanapi.controllers.RequestImage
import com.example.titanapi.controllers.RequestLogin
import com.example.titanapi.data.database.AppDatabase
import com.example.titanapi.views.CameraView
import com.example.titanapi.views.TitanMobApp

class MainActivity : ComponentActivity() {

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

        RequestLogin.setDatabase(db)
        RequestImage.setDatabase(db)
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