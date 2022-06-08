package com.example.friender2.repositories

import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.example.friender2.FrienderApplication
import com.example.friender2.Utils
import com.example.friender2.database.AppDatabase
import com.example.friender2.database.Profile
import com.example.friender2.randomProfileUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NullPointerException

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
            { error ->
                callback(null)
            }
        )

        requestQueue.add(fetchRequest)
    }

    fun saveCurrentViewedProfile(profile: Profile) {
        profileDao.deleteRejected()
        profileDao.saveProfile(profile)
    }

    fun fetchLatestProfile(callback: (Profile?) -> Unit) {
        callback(profileDao.getViewedProfile())
    }

    fun saveLatestAsFriend() {
        profileDao.addAsFriend()
    }

    fun fetchFriends(callback: (MutableList<Profile>) -> Unit) {
        callback(profileDao.getAllFriends())
    }

    fun deleteFriend(id: Long) {
        profileDao.delete(id)
    }

    fun undoDelete(profile: Profile) {
        profileDao.saveProfile(profile)
    }
}