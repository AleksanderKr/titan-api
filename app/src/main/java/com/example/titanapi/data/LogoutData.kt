package com.example.titanapi.data

data class LogoutData(
    val logout: String = ""
)

data class LogoutResponse(
    val message: String,
    val success: Boolean,
)