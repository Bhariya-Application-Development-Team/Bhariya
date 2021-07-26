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
class UserLogin_Test {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    fun userLogin_Test() {
        val textInputEditText = onView(
allOf(withId(R.id.etphonenumber),
childAtPosition(
childAtPosition(
withId(R.id.phonenumbertxt),
0),
0),
isDisplayed()))
        textInputEditText.perform(replaceText("9813456789"), closeSoftKeyboard())
        
        val textInputEditText2 = onView(
allOf(withId(R.id.etpassword),
childAtPosition(
childAtPosition(
withId(R.id.passwordtxt),
0),
0),
isDisplayed()))
        textInputEditText2.perform(replaceText("instrumenttest"), closeSoftKeyboard())
        
        val appCompatImageView = onView(
allOf(withId(R.id.btnlogin),
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
