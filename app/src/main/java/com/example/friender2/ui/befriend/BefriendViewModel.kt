package com.example.friender2.ui.befriend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.friender2.Utils
import com.example.friender2.database.Profile
import com.example.friender2.repositories.ProfileRepository

class BefriendViewModel : ViewModel() {
    private val repo = ProfileRepository()

    var pleaseWait: MutableLiveData<Boolean> = MutableLiveData()
    var viewedProfile: MutableLiveData<Profile> = MutableLiveData()
    var viewRetryButton: MutableLiveData<Boolean> = MutableLiveData()

    fun rejectFriend() {
        getNewProfile()
    }

    fun acceptFriend() {
        addToFriends()

        getNewProfile()
    }

    private fun getNewProfile() {
        pleaseWait.postValue(true)

        repo.fetchRandomProfile { profile, statusCode ->
            if (profile != null) {
                profile.imageUrl = Utils.getProfilePictureUrl(Utils.isMale(profile.gender))

                viewedProfile.postValue(profile)

                repo.saveCurrentViewedProfile(profile)
            } else {
                //TODO: Show error to viewer. Display refresh-button.
            }

            pleaseWait.postValue(false)
        }
    }

    fun getProfile() {
        pleaseWait.postValue(true)

        repo.fetchLatestProfile { profile ->
            if (profile != null) {
                viewedProfile.postValue(profile)
                pleaseWait.postValue(false)
            } else {
                getNewProfile()
            }
        }
    }

    private fun addToFriends() {
        repo.saveLatestAsFriend()
    }

}