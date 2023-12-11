package com.example.titanapi.data


import com.google.gson.JsonElement
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
        const val BASE_URL = "http://172.24.87.108:3000"
    }
}