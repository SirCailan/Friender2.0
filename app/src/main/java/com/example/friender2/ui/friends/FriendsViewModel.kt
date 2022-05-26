package com.example.friender2.ui.friends

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.friender2.database.Profile
import com.example.friender2.repositories.ProfileRepository

class FriendsViewModel : ViewModel() {
    private val repo = ProfileRepository()

    var friendsList: MutableLiveData<List<Profile>> = MutableLiveData()

    fun getFriendsList() {
        repo.fetchFriends { friends ->
            friendsList.postValue(friends)
        }
    }

    fun removeFriend(profile: Profile) {
        repo.deleteFriend(profile.id)
    }
}