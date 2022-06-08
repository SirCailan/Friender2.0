package com.example.friender2.ui.friends

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.friender2.database.Profile
import com.example.friender2.repositories.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FriendsViewModel : ViewModel() {
    private val repo = ProfileRepository()

    var friendsList: MutableLiveData<List<Profile>> = MutableLiveData()

    fun getFriendsList() {
        CoroutineScope(Dispatchers.IO).launch {
            repo.fetchFriends { friends ->
                friendsList.postValue(friends)
            }
        }
    }

    fun removeFriend(profile: Profile) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.deleteFriend(profile.id)
        }
    }
}