package com.example.titanapi

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.titanapi.data.LoginData
import com.example.titanapi.data.UserData
import com.example.titanapi.di.UserModule
import com.example.titanapi.views.TitanMobApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    private val loginApi = UserModule.provideLoginApi()
    private val BASE_URL = "http://10.0.2.2:3000"
    private val TAG: String = "CHECK_RESPONSE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TitanMobApp()
        }

        //sendLoginRequest()
        }

    private fun sendLoginRequest() {
        val api = UserModule.provideLoginApi()

        val logData = LoginData("titan", "titan123")

        api.loginUser(logData).enqueue(object : Callback<UserData>{
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if(response.isSuccessful) {
                    val responseTextView: TextView = findViewById(R.id.responseTextView)
                    responseTextView.text = response.body().toString()
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}