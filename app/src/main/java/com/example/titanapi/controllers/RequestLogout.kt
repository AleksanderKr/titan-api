package com.example.titanapi.controllers

import android.util.Log
import com.example.titanapi.data.LoginData
import com.example.titanapi.data.UserData
import com.example.titanapi.di.TitanMobAppRouter
import com.example.titanapi.di.ApiProvider
import com.example.titanapi.di.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object RequestLogout {
    private val loginApi = ApiProvider.provideLoginApi()

}