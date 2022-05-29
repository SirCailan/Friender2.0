package com.example.friender2.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.friender2.R
import com.example.friender2.Utils
import com.squareup.picasso.Picasso

class DetailsFragment() : Fragment() {
    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    lateinit var profileGender: ImageView
    lateinit var profileImage: ImageView
    lateinit var profileAge: TextView
    lateinit var profileName: TextView
    lateinit var profileLocation: TextView
    lateinit var profileOccupation: TextView
    lateinit var profileLoader: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getProfile(args.profileId)
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

        profileGender = view.findViewById(R.id.profile_gender_icon)
        profileImage = view.findViewById(R.id.profile_image)
        profileAge = view.findViewById(R.id.profile_age_text)
        profileName = view.findViewById(R.id.profile_name_text)
        profileLocation = view.findViewById(R.id.profile_place_text)
        profileOccupation = view.findViewById(R.id.profile_occupation_text)
        profileLoader = view.findViewById(R.id.profile_loader)

        setListeners()
    }

    private fun setListeners() {
        viewModel.pleaseWait.observe(viewLifecycleOwner) { loading ->
            //Shows progressbar when loading messages, and hides it when finished.
            when (loading) {
                true -> {
                    profileLoader.visibility = View.VISIBLE
                }
                false -> {
                    profileLoader.visibility = View.GONE
                }
            }
        }

        viewModel.viewedProfile.observe(viewLifecycleOwner) { profile ->
            if (Utils.isMale(profile.gender)) {
                profileGender.setImageResource(R.drawable.ic_male)
            } else {
                profileGender.setImageResource(R.drawable.ic_female)
            } // This sets the gender icon for the profile based on their gender.

            Picasso.get().load(profile.imageUrl).into(profileImage)
            //This loads the image on the URL into the ImageView.

            profileAge.text = Utils.getAge(profile.birthDate)
            profileName.text = Utils.getFullName(profile.firstName, profile.surname)
            profileLocation.text = Utils.getPlaceText(profile.address)
            profileOccupation.text = Utils.getEmploymentText(profile.employment)
        }
    }
}