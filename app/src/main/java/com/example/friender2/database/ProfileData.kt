package com.example.friender2.database

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.beust.klaxon.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "friends")
data class Profile(
    @Json(ignored = true)
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

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
) : Parcelable

@Parcelize
data class Occupation(
    val title: String = "Missing_Title",

    @Json(name = "key_skill")
    val keySkill: String = "Missing_Keyskill"
) : Parcelable

@Parcelize
data class Location(
    val city: String = "Missing_City",
    val country: String = "Missing_Country"
) : Parcelable