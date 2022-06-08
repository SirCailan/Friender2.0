package com.example.friender2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProfileDao {
    @Insert
    fun saveProfile(item: Profile)

    @Query("SELECT * FROM friends WHERE friend = 0" )
    fun getViewedProfile(): Profile

    @Query("SELECT * FROM friends WHERE friend = 1" )
    fun getAllFriends(): MutableList<Profile>

    @Query("SELECT * FROM friends WHERE id = :id")
    fun getProfile(id: Long): Profile

    @Query("DELETE FROM friends WHERE id = :id")
    fun delete(id: Long)

    @Query("DELETE FROM friends WHERE friend = 0")
    fun deleteRejected()

    @Query("UPDATE friends SET friend = 1 WHERE friend = 0")
    fun addAsFriend()
}