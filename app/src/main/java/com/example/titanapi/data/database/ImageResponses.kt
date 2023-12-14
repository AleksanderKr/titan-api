package com.example.titanapi.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class ImageResponses(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val benign: String,
    val malignant: String,
    val result: String,
    val sentTime: String
)
