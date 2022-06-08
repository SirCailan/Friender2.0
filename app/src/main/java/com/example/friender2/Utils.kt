package com.example.friender2

import com.example.friender2.database.Location
import com.example.friender2.database.Occupation
import java.time.LocalDate
import java.time.Period

object Utils {
    fun getAge(dateOfBirth: String): String {
        return try {
            val birthDates = dateOfBirth.split('-').map {
                it.toInt()
            }

            Period.between(
                LocalDate.of(birthDates[0], birthDates[1], birthDates[2]),
                LocalDate.now()
            ).years.toString()
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
    }

    fun getEmploymentText(employment: Occupation?): String {
        return if (employment == null) {
            "No occupation"
        } else {
            "Role: ${employment.title}\nSkill: ${employment.keySkill}"
        }
    }

    fun getPlaceText(address: Location): String {
        return "${address.city}, ${address.country}"
    }

    fun getProfilePictureUrl(isMale: Boolean): String {
        val imageNumber = (0..99).random()

        val genderString = when (isMale) {
            true -> {
                "men"
            }
            false -> {
                "women"
            }
        }

        return "https://randomuser.me/api/portraits/$genderString/$imageNumber.jpg"
    }
}