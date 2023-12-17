package com.example.titanapi.controllers

import android.util.Log
import com.example.titanapi.data.ApiResponse
import com.example.titanapi.data.ImageData
import com.example.titanapi.data.PixelData
import com.example.titanapi.data.database.AppDatabase
import com.example.titanapi.di.ApiProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CompletableFuture


object RequestImage {
    private val imgApi = ApiProvider.provideImageApi()
    private var sentData = ImageData("", null)
    private val task = "mvp prediction"
    private lateinit var titanDB: AppDatabase

    var responseDAO = ApiResponse(null)

    fun sendImageRequest(pixelArray: IntArray): CompletableFuture<ApiResponse> {
        val futureResponse = CompletableFuture<ApiResponse>()
        val pixelData = PixelData(pixelArray, "0")
        val jwt = "Bearer ${RequestLogin.logged_user.accessToken}"

        sentData = ImageData(task, pixelData)

        imgApi.sendImage(sentData, jwt).enqueue(object : Callback<ApiResponse> {

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val respData = response.body()?.data
                    val predictions = respData?.predictions?.firstOrNull()?.result
                    val name = RequestLogin.logged_user.user.username
                    val ben = predictions?.benign_prob
                    val mal = predictions?.malignant_prob
                    val res = predictions?.prediction

                    responseDAO = ApiResponse(respData)

                    CoroutineScope(Dispatchers.IO).launch {
                        if (ben != null && mal != null && res != null) {
                            titanDB.imageDao().insertImageLog(name, ben, mal, res)
                        }
                    }
                    futureResponse.complete(responseDAO)
                }
                else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Response", "Error Body: $errorBody")
                    futureResponse.complete(null)
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("TAG", "Network request failed: ${t.message}")
                futureResponse.complete(null)
            }
        })

        return futureResponse
    }
    fun setDatabase(database: AppDatabase) {
        titanDB = database
    }
}
