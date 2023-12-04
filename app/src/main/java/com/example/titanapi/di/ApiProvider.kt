package com.example.titanapi.di

import com.example.titanapi.data.ImageApi
import com.example.titanapi.data.LoginApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiProvider {

    fun provideLoginApi():LoginApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(LoginApi.BASE_URL)
            .build()
            .create(LoginApi::class.java)
    }

    fun provideImageApi():ImageApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ImageApi.BASE_URL)
            .build()
            .create(ImageApi::class.java)
    }
}