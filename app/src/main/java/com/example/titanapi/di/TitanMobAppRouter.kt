package com.example.titanapi.di

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


sealed class View {
    object LoginView: View()
    object CameraView: View()
}

object TitanMobAppRouter {
    var currentView: MutableState<View> = mutableStateOf(View.LoginView)

    fun routeTo(target: View) {
        currentView.value = target
    }
}