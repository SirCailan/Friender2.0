package com.example.friender2.ui.profile

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.friender2.R
import com.example.friender2.Utils
import com.example.friender2.database.Profile
import com.squareup.picasso.Picasso

//This class saves us from duplicating code wherever we need to use the profile view in a fragment.
//REMEMBER TO USE THE bindViews FUNCTION IN THE FRAGMENT'S onViewCreated !
class ProfileView {
    //Images
    private lateinit var profileGender: ImageView
    private lateinit var profileImage: ImageView

    //Text
    private lateinit var profileAge: TextView
    private lateinit var profileName: TextView
    private lateinit var profileLocation: TextView
    private lateinit var profileOccupation: TextView

    //ProgressBars
    private lateinit var profileLoader: ProgressBar

    fun bindViews(view: View) {
        //Images
        profileGender = view.findViewById(R.id.profile_gender_icon)
        profileImage = view.findViewById(R.id.profile_image)

        //Text
        profileAge = view.findViewById(R.id.profile_age_text)
        profileName = view.findViewById(R.id.profile_name_text)
        profileLocation = view.findViewById(R.id.profile_place_text)
        profileOccupation = view.findViewById(R.id.profile_occupation_text)

        //ProgressBars
        profileLoader = view.findViewById(R.id.profile_loader)
    }

    fun setLoaderVisibility(loading: Boolean) {
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

    fun changeProfile(profile: Profile) {
        setProfileGender(profile.gender)
        setProfileImage(profile.imageUrl)
        setProfileText(profile)
    }

    private fun setProfileGender(gender: String) {
        if (Utils.isMale(gender)) {
            profileGender.setImageResource(R.drawable.ic_male)
        } else {
            profileGender.setImageResource(R.drawable.ic_female)
        } // This sets the gender icon for the profile based on their gender.
    }

    private fun setProfileImage(imageUrl: String) {
        Picasso.get().load(imageUrl).into(profileImage)
        //This loads the image on the URL into the ImageView.
    }

    private fun setProfileText(profile: Profile) {
        profileAge.text = Utils.getAge(profile.birthDate)
        profileName.text = Utils.getFullName(profile.firstName, profile.surname)
        profileLocation.text = Utils.getPlaceText(profile.address)
        profileOccupation.text = Utils.getEmploymentText(profile.employment)
    }
}