package com.example.friender2.ui.friends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.friender2.R
import com.example.friender2.ui.profile.ProfileView

class DetailsFragment : Fragment() {
    private val viewModel: FriendsViewModel by activityViewModels()
    private val profileView = ProfileView()

    //Buttons
    private lateinit var deleteButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews(view)

        setClickListeners()

        viewModel.getSelectedFriend()?.let { profileView.changeProfile(it) }
    }

    private fun bindViews(view: View) {
        //Profile View
        profileView.bindViews(view)

        //Buttons
        deleteButton = view.findViewById(R.id.details_delete_friend_button)
    }

    private fun setClickListeners() {
        deleteButton.setOnClickListener {
            viewModel.removeFriend()
            findNavController().popBackStack()
            //Navigates back to friends screen.
        }
    }
}