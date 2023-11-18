package com.example.titanapi.di

import com.example.titanapi.data.LoginApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


object UserModule {

    fun provideLoginApi():LoginApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(LoginApi.BASE_URL)
            .build()
            .create(LoginApi::class.java)
    }
}