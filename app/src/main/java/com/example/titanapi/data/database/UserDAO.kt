package com.example.titanapi.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserLogDAO {
    @Query("INSERT INTO UserLog (username, loginTime, successful) VALUES (:name, CURRENT_TIMESTAMP, :success)")
    suspend fun insertUserLog(name: String, success: Boolean)

    @Query("SELECT * FROM UserLog WHERE id = :id")
    suspend fun getUserLogById(id: Int): UserLog?

    @Query("SELECT * FROM UserLog")
    suspend fun getAllUserLogs(): List<UserLog>

    @Query("DELETE FROM UserLog")
    suspend fun deleteAllUserLogs()
}
