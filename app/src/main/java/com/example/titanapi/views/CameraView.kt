package com.example.titanapi.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.titanapi.components.MainHeaderComponent
import com.example.titanapi.controllers.RequestLogin
import com.example.titanapi.ui.theme.AppBg

@Composable
fun CameraView() {
    Surface(
        color = AppBg,z
        modifier = Modifier
            .fillMaxSize()
            .background(AppBg)
            .padding(28.dp)) {
    }
    Log.d("TAG","CAMERAVIEW "+ RequestLogin.logged_user.toString())
    MainHeaderComponent(value = "tetet")

}

@Preview
@Composable
fun CameraViewPreview() {
    CameraView()
}