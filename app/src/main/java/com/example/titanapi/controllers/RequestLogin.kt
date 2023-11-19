package com.example.titanapi.controllers

import android.util.Log
import com.example.titanapi.data.LoginData
import com.example.titanapi.data.UserData
import com.example.titanapi.di.UserModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object RequestLogin {
    private val api = UserModule.provideLoginApi()
    private val logData = LoginData("titan", "titan123")
    fun sendLoginRequest() {
    api.loginUser(logData).enqueue(object : Callback<UserData> {
        override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
            if (response.isSuccessful) {
                val access_token = response.body()?.accessToken.toString()
                val refresh_token = response.body()?.refreshToken.toString()
                Log.d("TAG", access_token)
            }
        }

        override fun onFailure(call: Call<UserData>, t: Throwable) {
            Log.d("TAG", "fail")
        }

    })
}
}