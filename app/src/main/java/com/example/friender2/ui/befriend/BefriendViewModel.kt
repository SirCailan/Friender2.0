package com.example.friender2.ui.befriend

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
        //Getting a new profile will override the current viewed profile.
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

        //Starts a new thread.
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

    private fun saveViewedProfile(viewedProfile: Profile) {
        //Starts a new thread.
        CoroutineScope(Dispatchers.IO).launch {
            repo.saveCurrentViewedProfile(viewedProfile)
        }
    }

    private fun addToFriends() {
        viewedProfile.value?.let { profileToSave ->
            profileToSave.friend = true

            //Starts a new thread.
            CoroutineScope(Dispatchers.IO).launch {
                repo.saveAsFriend(profileToSave)
            }
        }
    }
}