package com.example.friender2.repositories

import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.example.friender2.FrienderApplication
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

    fun fetchRandomProfile(callback: (Boolean, Int) -> Unit) {
        val fetchRequest = StringRequest(
            Request.Method.GET,
            randomProfileUrl,
            { response ->
                val profile = Klaxon().parse<Profile>(response)

                if (profile != null) {
                    saveCurrentViewedProfile(profile)
                    callback(true, 200)
                } else {
                    callback(false, 500)
                }
            },
            { error ->
                val errorCode = error.networkResponse.statusCode
                callback(false, errorCode)
            }
        )

        requestQueue.add(fetchRequest)
    }

    private fun saveCurrentViewedProfile(profile: Profile) {
        CoroutineScope(Dispatchers.IO).launch {
            profileDao.deleteRejected()
            profileDao.saveProfile(profile)
        }
    }

    fun saveViewedAsFriend() {
        CoroutineScope(Dispatchers.IO).launch {
            profileDao.addFriend()
        }
    }

    fun fetchAllFriends(callback: (List<Profile>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            callback (profileDao.getAllFriends())
        }
    }

    fun deleteFriend(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            profileDao.delete(id)
        }
    }
}