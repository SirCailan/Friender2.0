package com.example.friender2.ui.befriend

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.friender2.Utils
import com.example.friender2.database.Profile
import com.example.friender2.repositories.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        repo.fetchRandomProfile { profile ->
            if (profile != null) {
                profile.imageUrl = Utils.getProfilePictureUrl(Utils.isMale(profile.gender))

                viewedProfile.postValue(profile)

                saveViewedProfile(profile)
            } else {
                viewRetryButton.postValue(true)
            }

            pleaseWait.postValue(false)
        }
    }

    fun getProfile() {
        pleaseWait.postValue(true)
        viewRetryButton.postValue(false)

        CoroutineScope(Dispatchers.IO).launch {
            repo.fetchLatestProfile { profile ->
                if (profile != null) {
                    viewedProfile.postValue(profile)
                    pleaseWait.postValue(false)
                } else {
                    getNewProfile()
                }
            }
        }
    }

    private fun addToFriends() {
        CoroutineScope(Dispatchers.IO).launch {
            repo.saveLatestAsFriend()
        }
    }

    private fun saveViewedProfile(viewedProfile: Profile) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.saveCurrentViewedProfile(viewedProfile)
        }
    }
}