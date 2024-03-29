package com.example.friender2.ui.friends

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.friender2.MainActivity
import com.example.friender2.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FriendsFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        launchFragmentInContainer<FriendsFragment>()
    }

    @Test
    fun testRecyclerViewIsVisible() {
        onView(withId(R.id.friends_recyclerview)).check(matches(isDisplayed()))
    }

    @Test
    fun undoButtonGoneAsDefault() {
        onView(withId(R.id.friends_undo_delete_button)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }
}