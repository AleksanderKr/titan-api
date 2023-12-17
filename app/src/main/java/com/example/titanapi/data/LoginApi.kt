package com.example.titanapi.data


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface LoginApi {

    @POST("/api/users/login")
    fun loginUser(@Body loginData: LoginData): Call<UserData>

    @POST("/api/users/logout")
    fun logoutUser(@Body logoutData: LogoutData): Call<LogoutResponse>
    companion object {

        const val BASE_URL = "http://192.168.137.1:3000"
    }
}