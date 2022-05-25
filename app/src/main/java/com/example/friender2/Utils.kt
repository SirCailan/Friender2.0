package com.example.friender2

import com.example.friender2.database.Location
import com.example.friender2.database.Occupation
import java.util.*

object Utils {
    fun getAge(dateOfBirth: String): String {
        val thisYear = Calendar.getInstance().get(Calendar.YEAR)

        return try {
            val year = dateOfBirth.substring(0, 4).toInt()
            (thisYear - year).toString()
        } catch (e: Exception) {
            "-"
        }
    }

    fun getFullName(firstName: String, lastName: String): String {
        return "$firstName $lastName"
    }

    fun isMale(gender: String): Boolean {
        return when (gender.lowercase()) {
            "male" -> {
                true
            }
            "genderfluid" -> {
                true
            }
            "polygender" -> {
                true
            }
            "agender" -> {
                true
            }
            else -> {
                false
            }
        }
        //This is not accurate, but it is at least streamlined now.
    }

    fun getEmploymentText(employment: Occupation?): String {
        return if (employment == null) {
            "Hobo..."
            //TODO: This is very unkind. Change this.
        } else {
            "Role: ${employment.title}\nSkill: ${employment.keySkill}"
        }
    }

    fun getPlaceText(address: Location): String {
        return "${address.city}, ${address.country}"
    }

    fun getProfilePictureUrl(gender: String): String {
        val number = (0..99).random()
        val isMale = (0..1).random()

        return "https://randomuser.me/api/portraits/${if (isMale == 0) "men" else "women"}/$number.jpg"
    }
}