package com.example.friender2.ui.befriend

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.friender2.MainActivity
import com.example.friender2.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class BefriendFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testRejectButton() {
        val scenario = launchFragmentInContainer<BefriendFragment>()

        onView(withId(R.id.reject_button)).check(matches(isDisplayed()))

        //Click Reject
        onView(withId(R.id.reject_button)).perform(click())
    }

    @Test
    fun testAcceptButton() {
        val scenario = launchFragmentInContainer<BefriendFragment>()

        onView(withId(R.id.accept_button)).check(matches(isDisplayed()))

        //Click Accept
        onView(withId(R.id.accept_button)).perform(click())
    }

    @Test
    fun testNavigate() {
        val scenario = launchFragmentInContainer<BefriendFragment>()
        val navController = mock(NavController::class.java)

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.my_friends_button)).check(matches(isDisplayed()))

        onView(withId(R.id.my_friends_button)).perform(click())

        verify(navController).navigate(BefriendFragmentDirections.actionBefriendFragmentToFriendsFragment())
    }
}