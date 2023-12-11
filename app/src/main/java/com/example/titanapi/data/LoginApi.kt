package com.example.titanapi.data


import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST


interface LoginApi {

    @POST("/api/users/login")
    fun loginUser(@Body loginData: LoginData): Call<UserData>

    @POST("/api/users/logout")
    fun logoutUser(@Body logoutData: LogoutData): Call<LogoutResponse>
    companion object {

        const val BASE_URL = "http://172.24.87.108:3000"
    }
}