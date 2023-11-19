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
    private var logData = LoginData("","")
    var access_token = ""
    var refresh_token = ""
    fun sendLoginRequest(identifier: String, password: String) {

        logData = LoginData(identifier, password)
        api.loginUser(logData).enqueue(object : Callback<UserData> {

            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                access_token = response.body()?.accessToken.toString()
                refresh_token = response.body()?.refreshToken.toString()
                Log.d("TAG", access_token)
                //TitanMobAppRouter.routeTo(View.CameraView)
            }
                else {
                    Log.d("TAG", "Unauthorized")
                }
        }

        override fun onFailure(call: Call<UserData>, t: Throwable) {
            Log.d("TAG", "failed to connect")
        }

    })
}
}