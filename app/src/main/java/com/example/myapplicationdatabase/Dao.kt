package com.example.myapplicationdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM User")
    suspend fun getAllUsers(): List<User>
}
