package com.example.titanapi.data

data class PredictionResult(
    val benign_prob: String,
    val malignant_prob: String,
    val prediction  : String
)

data class Prediction(
    val result: PredictionResult,
    val service: String
)

data class ApiResponse(
    val data: Data? // Data to nowa klasa zawierająca resztę pól
)

data class Data(
    val pixelData: IntArray?,
    val predictions: List<Prediction>?,
    val tumorSize: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Data

        if (pixelData != null) {
            if (other.pixelData == null) return false
            if (!pixelData.contentEquals(other.pixelData)) return false
        } else if (other.pixelData != null) return false
        if (predictions != other.predictions) return false
        return tumorSize == other.tumorSize
    }

    override fun hashCode(): Int {
        var result = pixelData?.contentHashCode() ?: 0
        result = 31 * result + (predictions?.hashCode() ?: 0)
        result = 31 * result + (tumorSize?.hashCode() ?: 0)
        return result
    }
}
