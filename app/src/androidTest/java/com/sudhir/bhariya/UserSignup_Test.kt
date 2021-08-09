package com.sudhir.bhariya


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class UserSignup_Test {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SignUpActivity::class.java)

    @Test
    fun userSignup_Test() {
        val materialTextView = onView(
allOf(withId(R.id.tvSignup), withText("Register here"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
5),
1),
isDisplayed()))
        materialTextView.perform(click())
        
        val textInputEditText = onView(
allOf(withId(R.id.phonenumber),
childAtPosition(
childAtPosition(
withId(R.id.pnumtext),
0),
0),
isDisplayed()))
        textInputEditText.perform(replaceText("9813456789"), closeSoftKeyboard())
        
        val textInputEditText2 = onView(
allOf(withId(R.id.fullname),
childAtPosition(
childAtPosition(
withId(R.id.fnametext),
0),
0),
isDisplayed()))
        textInputEditText2.perform(replaceText("Register Test"), closeSoftKeyboard())
        
        val textInputEditText3 = onView(
allOf(withId(R.id.address),
childAtPosition(
childAtPosition(
withId(R.id.addtext),
0),
0),
isDisplayed()))
        textInputEditText3.perform(replaceText("Kathmandu"), closeSoftKeyboard())
        
        val textInputEditText4 = onView(
allOf(withId(R.id.password),
childAtPosition(
childAtPosition(
withId(R.id.pwd),
0),
0),
isDisplayed()))
        textInputEditText4.perform(replaceText("instrumenttest"), closeSoftKeyboard())
        
        val textInputEditText5 = onView(
allOf(withId(R.id.confirmpassword),
childAtPosition(
childAtPosition(
withId(R.id.passcon),
0),
0),
isDisplayed()))
        textInputEditText5.perform(replaceText("instrumenttest"), closeSoftKeyboard())
        
        val appCompatImageView = onView(
allOf(withId(R.id.register),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
4),
1),
isDisplayed()))
        appCompatImageView.perform(click())
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
