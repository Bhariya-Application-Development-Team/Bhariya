package com.sudhir.bhariya


import androidx.test.espresso.DataInteraction
import androidx.test.espresso.ViewInteraction
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

import androidx.test.InstrumentationRegistry.getInstrumentation
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*

import com.sudhir.bhariya.R

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.`is`

@LargeTest
@RunWith(AndroidJUnit4::class)
class RideHistory_test {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
"android.permission.CAMERA",
"android.permission.WRITE_EXTERNAL_STORAGE")

    @Test
    fun rideHistory_test() {
        val textInputEditText = onView(
allOf(withId(R.id.etphonenumber),
childAtPosition(
childAtPosition(
withId(R.id.phonenumbertxt),
0),
0),
isDisplayed()))
        textInputEditText.perform(replaceText("9813910902"), closeSoftKeyboard())
        
        val textInputEditText2 = onView(
allOf(withId(R.id.etpassword),
childAtPosition(
childAtPosition(
withId(R.id.passwordtxt),
0),
0),
isDisplayed()))
        textInputEditText2.perform(replaceText("123@wang"), closeSoftKeyboard())
        
        val appCompatImageView = onView(
allOf(withId(R.id.btnlogin),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
4),
1),
isDisplayed()))
        appCompatImageView.perform(click())
        
        val bottomNavigationItemView = onView(
allOf(withId(R.id.ic_history), withContentDescription("History"),
childAtPosition(
childAtPosition(
withId(R.id.botton_nav),
0),
1),
isDisplayed()))
        bottomNavigationItemView.perform(click())
        
        val appCompatImageView2 = onView(
allOf(childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1),
0),
isDisplayed()))
        appCompatImageView2.perform(click())
        
        val appCompatImageView3 = onView(
allOf(childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1),
1),
isDisplayed()))
        appCompatImageView3.perform(click())
        }
    
    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
    }
