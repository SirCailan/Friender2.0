package com.example.friender2

import com.example.friender2.database.Location
import com.example.friender2.database.Occupation
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate

class UtilsTest {
    @Test
    fun `getAge - 25 years when birthdate is 1997-02-11`() {
        val birthDate: String = "1997-02-11"
        val compareDate: LocalDate = LocalDate.of(2022, 5, 26)

        assertEquals("25", Utils.getAge(birthDate, compareDate))
    }

    @Test
    fun `getAge - Dash returned when format is wrong`() {
        val birthDate: String = "19970211"

        assertEquals("-", Utils.getAge(birthDate))
    }

    @Test
    fun `getAge - Return zero if birth date is in the future`() {
        val birthDate: String = "2023-02-13"
        val compareDate: LocalDate = LocalDate.of(2022, 5, 26)

        assertEquals("0", Utils.getAge(birthDate, compareDate))
    }

    @Test
    fun `getFullName - Return one string when provided two strings`() {
        val firstName: String = "John"
        val surname: String = "Doe"

        assertEquals("John Doe", Utils.getFullName(firstName, surname))
    }

    @Test
    fun `getFullName - Name contains weird characters`() {
        val firstName: String = "*/^_-<->/@ /"
        val surname: String = "}{8€242*-+=,."

        assertEquals("*/^_-<->/@ / }{8€242*-+=,.", Utils.getFullName(firstName, surname))
    }

    @Test
    fun `getFullName - Empty surname`() {
        val firstName: String = "John"
        val surname: String = ""

        assertEquals("John ", Utils.getFullName(firstName, surname))
    }

    @Test
    fun `getFullName - Empty firstname`() {
        val firstName: String = ""
        val surname: String = "Doe"

        assertEquals(" Doe", Utils.getFullName(firstName, surname))
    }

    @Test
    fun `getFullName - Empty first and last name`() {
        val firstName: String = ""
        val surname: String = ""

        assertEquals(" ", Utils.getFullName(firstName, surname))
    }

    @Test
    fun `isMale - Male when gender is male`() {
        assert(Utils.isMale("male"))
    }

    @Test
    fun `isMale - Male when gender is male uppercase`() {
        assert(Utils.isMale("MALE"))
    }

    @Test
    fun `isMale - Not male when gender is female`() {
        assertFalse(Utils.isMale("female"))
    }

    @Test
    fun `isMale - Male when genderfluid`() {
        assert(Utils.isMale("genderfluid"))
    }

    @Test
    fun `isMale - Male when polygender`() {
        assert(Utils.isMale("polygender"))
    }

    @Test
    fun `isMale - Male when agender`() {
        assert(Utils.isMale("agender"))
    }

    @Test
    fun `isMale - Not male when nonsense string`() {
        assertFalse(Utils.isMale("gfagatfa"))
    }

    @Test
    fun `getEmploymentText - Expected behavior, parameters input`() {
        val employment: Occupation = Occupation("Tester", "Testing")

        assertEquals("Role: Tester\nSkill: Testing", Utils.getEmploymentText(employment))
    }

    @Test
    fun `getEmploymentText - No skill`() {
        val employment: Occupation = Occupation(title = "Tester")

        assertEquals("Role: Tester\nSkill: Missing_Keyskill", Utils.getEmploymentText(employment))
    }

    @Test
    fun `getEmploymentText - No role`() {
        val employment: Occupation = Occupation(keySkill = "Testing")

        assertEquals("Role: Missing_Title\nSkill: Testing", Utils.getEmploymentText(employment))
    }

    @Test
    fun `getEmploymentText - No role or skills`() {
        val employment: Occupation = Occupation()

        assertEquals("Role: Missing_Title\nSkill: Missing_Keyskill", Utils.getEmploymentText(employment))
    }

    @Test
    fun `getPlaceText - Expected behavior, parameters input`() {
        val location: Location = Location("Oslo", "Norway")

        assertEquals("Oslo, Norway", Utils.getPlaceText(location))
    }

    @Test
    fun `getPlaceText - No city`() {
        val location: Location = Location(country = "Norway")

        assertEquals("Missing_City, Norway", Utils.getPlaceText(location))
    }

    @Test
    fun `getPlaceText - No country`() {
        val location: Location = Location(city = "Oslo")

        assertEquals("Oslo, Missing_Country", Utils.getPlaceText(location))
    }

    @Test
    fun `getPlaceText - No city or country`() {
        val location: Location = Location()

        assertEquals("Missing_City, Missing_Country", Utils.getPlaceText(location))
    }

    @Test
    fun `getProfilePictureUrl - Correct URL when male`() {
        val url: String = Utils.getProfilePictureUrl(true)
        val regex = "https://randomuser.me/api/portraits/men/".toRegex()

        assertTrue(regex.containsMatchIn(url))
    }

    @Test
    fun `getProfilePictureUrl - Correct URL when female`() {
        val url: String = Utils.getProfilePictureUrl(false)
        val regex = "https://randomuser.me/api/portraits/women/".toRegex()

        assertTrue(regex.containsMatchIn(url))
    }
}