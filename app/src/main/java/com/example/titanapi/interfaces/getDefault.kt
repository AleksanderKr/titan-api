package com.example.titanapi.interfaces

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @get:GET("/")
    val data: Call<Any?>?
}