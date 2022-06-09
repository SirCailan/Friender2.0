package com.example.friender2.ui.friends

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.friender2.MainActivity
import com.example.friender2.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Before
    fun setup() {
        launchFragmentInContainer<DetailsFragment>()
    }

    @Test
    fun deleteButtonVisible() {
        onView(withId(R.id.details_delete_friend_button)).check(matches(isDisplayed()))
    }

    @Test
    fun profileCardVisible()  {
        onView(withId(R.id.details_profile_card)).check(matches(isDisplayed()))
    }
}
