package com.example.titanapi.data.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ImageDAO {
    @Query("INSERT INTO ImageResponses (username, benign, malignant, result, sentTime) VALUES (:name, :ben, :mal, :res, CURRENT_TIMESTAMP)")
    suspend fun insertImageLog(name: String, ben: String, mal: String, res: String)

    @Query("SELECT * FROM ImageResponses WHERE id = :id")
    suspend fun getResponseById(id: Int): ImageResponses?

    @Query("SELECT * FROM ImageResponses")
    suspend fun getAllResponses(): List<ImageResponses>

    @Query("DELETE FROM ImageResponses")
    suspend fun deleteAllImageLogs()
}
