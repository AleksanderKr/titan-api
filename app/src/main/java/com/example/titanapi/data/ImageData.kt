package com.example.titanapi.data

data class ImageData(
    val task: String,
    val data: PixelData
)

data class PixelData(
    val pixelData: List<Int>,
    val tumorSize: String
)