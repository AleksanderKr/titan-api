package com.example.titanapi.data


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface ImageApi {

    @POST("/api/pipeline")
    fun sendImage(
        @Body imageData: ImageData,
        @Header("Authorization") token: String
        ): Call<ApiResponse>

    companion object {
        const val BASE_URL = "http://192.168.137.1:3000"
    }
}