package com.example.titanapi.views

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.titanapi.di.TitanMobAppRouter
import com.example.titanapi.di.View
import com.example.titanapi.ui.theme.AppBg


@Composable
fun TitanMobApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppBg
    ) {
        Crossfade(targetState = TitanMobAppRouter.currentView, label = "") { currentState ->
            when(currentState.value) {
                is View.LoginViewObj -> {
                    LoginFormView()
                }
                is View.CameraViewObj -> {
                    CameraView()
                }
            }
        }
    }
}
