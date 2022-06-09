package com.example.friender2.repositories

import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.example.friender2.FrienderApplication
import com.example.friender2.database.AppDatabase
import com.example.friender2.database.Profile
import com.example.friender2.randomProfileUrl

class ProfileRepository {
    private val appContext = FrienderApplication.application.applicationContext
    private val profileDao = AppDatabase.getDatabase(appContext).getDao()
    private val requestQueue = Volley.newRequestQueue(appContext)

    fun fetchRandomProfile(callback: (Profile?) -> Unit) {
        val fetchRequest = StringRequest(
            Request.Method.GET,
            randomProfileUrl,
            { response ->
                val profile = Klaxon().parse<Profile>(response)

                callback(profile)
            },
            { _ ->
                callback(null)
            }
        )

        requestQueue.add(fetchRequest)
    }

    fun saveCurrentViewedProfile(profile: Profile) {
        profileDao.deleteRejected()
        //Deletes saved profiles that are not marked as friends.
        profileDao.saveProfile(profile)
        //Saves the new profile. Not marked as friend.
    }

    fun fetchLatestProfile(callback: (Profile?) -> Unit) {
        callback(profileDao.getViewedProfile())
        //Fetches one profile not marked as friend.
        //There should only be one profile not marked as friend at a time, but the dao limits it anyways.
    }

    fun saveAsFriend(profileToSave: Profile) {
        profileDao.addAsFriend(profileToSave)
        //Saves specified profile as friend.
    }

    fun fetchFriends(callback: (MutableList<Profile>) -> Unit) {
        callback(profileDao.getAllFriends())
        //Fetches all profiles marked as friends.
    }

    fun deleteFriend(id: Long) {
        profileDao.delete(id)
        //Deletes a profile specified by an id.
    }
}