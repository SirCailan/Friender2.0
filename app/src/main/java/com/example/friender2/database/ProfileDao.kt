package com.example.friender2.database

import androidx.room.*

@Dao
interface ProfileDao {
    @Insert
    fun saveProfile(item: Profile)

    @Query("SELECT * FROM friends WHERE friend = 0 LIMIT 1" )
    fun getViewedProfile(): Profile

    @Query("DELETE FROM friends WHERE friend = 0")
    fun deleteRejected()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAsFriend(profileToSave: Profile)

    @Query("SELECT * FROM friends WHERE friend = 1" )
    fun getAllFriends(): MutableList<Profile>

    @Query("SELECT * FROM friends WHERE id = :id")
    fun getProfile(id: Long): Profile

    @Query("DELETE FROM friends WHERE id = :id")
    fun delete(id: Long)
}