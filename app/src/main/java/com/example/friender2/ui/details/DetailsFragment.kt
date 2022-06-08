package com.example.friender2.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.friender2.R
import com.example.friender2.ui.profile.ProfileView

class DetailsFragment : Fragment() {
    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()
    private val profileView = ProfileView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getProfile(args.profileId)
        //Done in onCreate to fetch as soon as possible.
    }

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

        setObservers()
    }

    private fun bindViews(view: View) {
        profileView.bindViews(view)
    }

    private fun setObservers() {
        viewModel.pleaseWait.observe(viewLifecycleOwner) { loading ->
            profileView.setLoaderVisibility(loading)
        }

        viewModel.viewedProfile.observe(viewLifecycleOwner) { newProfile ->
            profileView.changeProfile(newProfile)
        }
    }
}