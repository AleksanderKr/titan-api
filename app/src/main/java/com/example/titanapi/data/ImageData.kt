package com.example.titanapi.data

data class ImageData(
    val task: String,
    val data: PixelData?
)

data class PixelData(
    val pixelData: IntArray,
    val tumorSize: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PixelData

        if (!pixelData.contentEquals(other.pixelData)) return false
        return tumorSize == other.tumorSize
    }

    override fun hashCode(): Int {
        var result = pixelData.contentHashCode()
        result = 31 * result + tumorSize.hashCode()
        return result
    }
}