package com.example.friender2.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beust.klaxon.Json

@Entity(tableName = "friends")
data class Profile(
    @PrimaryKey val id: Long,

    @Json(name = "first_name")
    val firstName: String,

    @Json(name = "last_name")
    val surname: String,
    val email: String,
    val gender: String,

    @Json(name = "date_of_birth")
    val birthDate: String,

    @Embedded
    val employment: Occupation,

    @Embedded
    val address: Location,
    var imageUrl: String = "",
    var friend: Boolean = false
)

data class Occupation(
    val title: String,

    @Json(name = "key_skill")
    val keySkill: String
)

data class Location(
    val city: String,
    val country: String
)