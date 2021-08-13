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
class VehicleANDLabour_Test {

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
    fun vehicleANDLabour_Test() {
        val appCompatImageView = onView(
allOf(withId(R.id.btnlogin),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
4),
1),
isDisplayed()))
        appCompatImageView.perform(click())
        
        val linearLayout = onView(
allOf(withId(R.id.mediumButton),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
1)))
        linearLayout.perform(scrollTo(), click())
        
        val materialTextView = onView(
allOf(withId(R.id.btnadd), withText("+"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
2),
3),
isDisplayed()))
        materialTextView.perform(click())
        
        val appCompatButton = onView(
allOf(withId(R.id.btnProceed), withText("Proceed"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
1),
isDisplayed()))
        appCompatButton.perform(click())
        
        val imageView = onView(
allOf(withContentDescription("My Location"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.FrameLayout")),
2),
0),
isDisplayed()))
        imageView.perform(click())
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
