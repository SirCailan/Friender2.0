package com.example.friender2.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.friender2.database.Profile
import com.example.friender2.repositories.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
    private val repo = ProfileRepository()

    var pleaseWait: MutableLiveData<Boolean> = MutableLiveData()
    var viewedProfile: MutableLiveData<Profile> = MutableLiveData()

    fun getProfile(profileId: Long) {
        pleaseWait.postValue(true)


        CoroutineScope(Dispatchers.IO).launch {
            repo.fetchProfile(profileId) { profile ->
                profile?.let {
                    viewedProfile.postValue(it)
                }
                pleaseWait.postValue(false)
            }
        }
    }
}