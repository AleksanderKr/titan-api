package com.example.titanapi.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class UserLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val loginTime: String,
    val successful: Boolean
)
