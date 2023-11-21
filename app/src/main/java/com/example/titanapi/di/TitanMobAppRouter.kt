package com.example.titanapi.di

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


sealed class View {
    object LoginViewObj: View()
    object CameraViewObj: View()
}

object TitanMobAppRouter {
    var currentView: MutableState<View> = mutableStateOf(View.LoginViewObj)

    fun routeTo(target: View) {
        currentView.value = target
    }
}