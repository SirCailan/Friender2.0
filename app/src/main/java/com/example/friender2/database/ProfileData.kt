package com.example.friender2.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beust.klaxon.Json

@Entity(tableName = "friends")
data class Profile(
    @PrimaryKey
    val id: Long,

    @Json(name = "first_name")
    val firstName: String = "Missing_Firstname",

    @Json(name = "last_name")
    val surname: String = "Missing_Surname",
    val email: String = "Missing_Email",
    val gender: String = "Missing_Gender",

    @Json(name = "date_of_birth")
    val birthDate: String = "Missing_Birthdate",

    @Embedded
    val employment: Occupation = Occupation(),

    @Embedded
    val address: Location = Location(),

    var imageUrl: String = "Missing_Image",
    var friend: Boolean = false
)

data class Occupation(
    val title: String = "Missing_Title",

    @Json(name = "key_skill")
    val keySkill: String = "Missing_Keyskill"
)

data class Location(
    val city: String = "Missing_City",
    val country: String = "Missing_Country"
)