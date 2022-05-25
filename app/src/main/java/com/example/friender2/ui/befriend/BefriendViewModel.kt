package com.example.friender2.ui.befriend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.friender2.database.Profile
import com.example.friender2.repositories.ProfileRepository

class BefriendViewModel : ViewModel() {
    private val profileRepository = ProfileRepository()

    var pleaseWait: MutableLiveData<Boolean> = MutableLiveData()
    var viewedProfile: MutableLiveData<Profile> = MutableLiveData()

    fun rejectFriend() {
        getNewProfile()
    }

    fun acceptFriend() {
        addToFriends()

        getNewProfile()
    }

    fun getNewProfile() {
        pleaseWait.postValue(true)

        profileRepository.fetchRandomProfile { success, statusCode ->

        }
    }

    private fun addToFriends() {

    }

}