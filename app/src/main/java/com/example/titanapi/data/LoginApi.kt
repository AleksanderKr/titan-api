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

    companion object {

        const val BASE_URL = "http://172.20.1.165:3000"
    }
}