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

class ProfileRepository {
    private val appContext = FrienderApplication.application.applicationContext
    private val profileDao = AppDatabase.getDatabase(appContext).getDao()
    private val requestQueue = Volley.newRequestQueue(appContext)

    fun fetchRandomProfile(callback: (Profile?, Int) -> Unit) {
        val fetchRequest = StringRequest(
            Request.Method.GET,
            randomProfileUrl,
            { response ->
                val profile = Klaxon().parse<Profile>(response)

                callback(profile, 200)
            },
            { error ->
                val errorCode = error.networkResponse.statusCode
                callback(null, errorCode)
            }
        )

        requestQueue.add(fetchRequest)
    }

    fun saveCurrentViewedProfile(profile: Profile) {
        CoroutineScope(Dispatchers.IO).launch {
            profileDao.deleteRejected()
            profileDao.saveProfile(profile)
        }
    }

    fun fetchLatestProfile(callback: (Profile?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            callback(profileDao.getViewedProfile())
        }
    }

    fun saveLatestAsFriend() {
        CoroutineScope(Dispatchers.IO).launch {
            profileDao.addAsFriend()
        }
    }

    fun fetchFriends(callback: (List<Profile>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            callback (profileDao.getAllFriends())
        }
    }

    fun deleteFriend(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            profileDao.delete(id)
        }
    }

    fun fetchProfile(id: Long, callback: (Profile?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            callback (profileDao.getProfile(id))
        }
    }
}