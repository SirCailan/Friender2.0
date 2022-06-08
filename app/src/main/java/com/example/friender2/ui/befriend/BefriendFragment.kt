package com.example.friender2.ui.befriend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.friender2.ui.profile.ProfileView
import com.example.friender2.R

class BefriendFragment : Fragment() {
    private val viewModel: BefriendViewModel by viewModels()
    private val profileView = ProfileView()

    //ImageButtons
    private lateinit var rejectButton: ImageButton
    private lateinit var acceptButton: ImageButton

    //Buttons
    private lateinit var friendsButton: Button
    private lateinit var retryButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getProfile()
        //Done in onCreate to prevent unnecessary fetching should the user navigate to another
        //screen and back to this fragment. Also to start fetching as soon as possible.
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_befriend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews(view)

        setClickListeners()

        setObservers()
    }

    private fun bindViews(view: View) {
        //Profile View
        profileView.bindViews(view)

        //ImageButtons
        rejectButton = view.findViewById(R.id.reject_button)
        acceptButton = view.findViewById(R.id.accept_button)

        //Buttons
        friendsButton = view.findViewById(R.id.my_friends_button)
        retryButton = view.findViewById(R.id.profile_retry_button)
        //The retry button should only show if the automatic fetch fails.
    }

    private fun setClickListeners() {
        rejectButton.setOnClickListener {
            viewModel.rejectFriend()
            //Discards viewed profile and fetches new profile.
        }

        acceptButton.setOnClickListener {
            viewModel.acceptFriend()
            //Saves viewed profile as friend and fetches new profile.
        }

        friendsButton.setOnClickListener {
            findNavController().navigate(BefriendFragmentDirections.actionBefriendFragmentToFriendsFragment())
            //Navigates to friends list.
        }

        retryButton.setOnClickListener {
            viewModel.getProfile()
            //This tries fetching a new profile.
            //To be used in case the automatic fetch fails for any reason.
        }
    }

    private fun setObservers() {
        viewModel.pleaseWait.observe(viewLifecycleOwner) { loading ->
            profileView.setLoaderVisibility(loading)
        }

        viewModel.viewedProfile.observe(viewLifecycleOwner) { profile ->
            profileView.changeProfile(profile)
        }

        viewModel.viewRetryButton.observe(viewLifecycleOwner) { viewRetryButton ->
            if (viewRetryButton) {
                retryButton.visibility = View.VISIBLE
            } else {
                retryButton.visibility = View.GONE
            }
            //The retry button should be gone as default. Show it if the fetch fails.
        }
    }
}