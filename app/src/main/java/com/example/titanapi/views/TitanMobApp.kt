package com.example.titanapi.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.titanapi.ui.theme.AppBg


@Composable
fun TitanMobApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppBg
    ) {
        LoginFormView()
    }
}
