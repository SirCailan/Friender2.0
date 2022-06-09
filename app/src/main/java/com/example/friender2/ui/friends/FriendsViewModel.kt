package com.example.friender2.ui.friends

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.friender2.database.Profile
import com.example.friender2.repositories.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//This ViewModel is shared between the Friends and Details fragment, for easy data sharing.
class FriendsViewModel : ViewModel() {
    private val repo = ProfileRepository()

    //Friend variables
    var friendsList: MutableLiveData<MutableList<Profile>> = MutableLiveData()
    private var selectedFriend: Profile? = null
    private var selectedIndexPosition: Int = 0

    //Visibility variables
    var undoButtonVisible: MutableLiveData<Boolean> = MutableLiveData()

    fun getFriendsList() {
        CoroutineScope(Dispatchers.IO).launch {
            repo.fetchFriends { friends ->
                friendsList.postValue(friends)
            }
        }
    }

    fun removeFriend() {
        selectedFriend?.id?.let { id ->
            CoroutineScope(Dispatchers.IO).launch {
                repo.deleteFriend(id)
            }
        }

        friendsList.value?.remove(selectedFriend)
        //Removes current selected friend from list.
        //Does not trigger the observe function.

        undoButtonVisible.postValue(true)
    }

    fun undoRemove() {
        selectedFriend?.let { profile ->
            //addToListObserved(profile)

            CoroutineScope(Dispatchers.IO).launch {
                repo.saveAsFriend(profile)
            }
        }

        undoButtonVisible.postValue(false)
    }

    fun selectProfile(profile: Profile, indexPosition: Int) {
        selectedFriend = profile
        selectedIndexPosition = indexPosition

        undoButtonVisible.postValue(false)
    }

    fun getSelectedFriend(): Profile? {
        return selectedFriend
    }

    fun getSelectedIndexPosition(): Int {
        return selectedIndexPosition
    }
}