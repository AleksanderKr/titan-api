package com.example.titanapi.controllers

import android.util.Log
import androidx.room.Room
import com.example.titanapi.data.LoginData
import com.example.titanapi.data.UserData
import com.example.titanapi.data.database.AppDatabase
import com.example.titanapi.di.TitanMobAppRouter
import com.example.titanapi.di.ApiProvider
import com.example.titanapi.di.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object RequestLogin {
    private val loginApi = ApiProvider.provideLoginApi()
    private var log_data = LoginData("","")
    var logged_user = UserData("",UserData.User(0,"","",0),"")
    private lateinit var titanDB: AppDatabase

    var access_token = ""
    var refresh_token = ""
    var user_id = 0
    var username = ""
    var user_email = ""
    var user_role = 0
    fun sendLoginRequest(identifier: String, password: String) {

        log_data = LoginData(identifier, password)
        loginApi.loginUser(log_data).enqueue(object : Callback<UserData> {

            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                    access_token = response.body()?.accessToken.toString()
                    refresh_token = response.body()?.refreshToken.toString()
                    user_id = response.body()?.user?.id!!
                    username = response.body()?.user!!.username
                    user_email = response.body()?.user!!.email
                    user_role = response.body()?.user!!.roleId

                    logged_user = UserData(refresh_token, UserData.User(user_id, username, user_email, user_role), access_token)


                    CoroutineScope(Dispatchers.IO).launch {
                        titanDB.userLogDao().insertUserLog(username, true)
                    }
                    TitanMobAppRouter.routeTo(View.CameraViewObj)
                }
                    else {
                    CoroutineScope(Dispatchers.IO).launch {
                        titanDB.userLogDao().insertUserLog(username, false)
                    }
                        Log.d("TAG", "Unauthorized")
                    }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                CoroutineScope(Dispatchers.IO).launch {
                    titanDB.userLogDao().insertUserLog(username, false)
                }
                Log.d("TAG", "failed to connect")
            }

        })
    }

    fun setDatabase(database: AppDatabase) {
        titanDB = database
    }
}