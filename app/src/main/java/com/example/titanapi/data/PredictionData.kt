package com.example.titanapi.data

data class PredictionResult(
    val benignProb: String,
    val malignantProb: String,
    val prediction: String
)

data class Prediction(
    val result: PredictionResult,
    val service: String
)

data class ApiResponse(
    val pixelData: List<Int>,
    val predictions: List<Prediction>,
    val tumorSize: String
)