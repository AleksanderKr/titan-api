package com.example.titanapi.data

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("refreshToken")
    val refreshToken: String,

    @SerializedName("user")
    val user: User,

    @SerializedName("accessToken")
    val accessToken: String
) {
    data class User(
        @SerializedName("id")
        val id: Int,

        @SerializedName("username")
        val username: String,

        @SerializedName("email")
        val email: String,

        @SerializedName("roleId")
        val roleId: Int
    )
}
