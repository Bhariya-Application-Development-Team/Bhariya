package com.sudhir.bhariya

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@LargeTest
@RunWith(JUnit4::class)
class DriverSignup_Test {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )

    @Test
    fun driverSignup_Test() {
        val materialTextView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.tvSignupdriver),
                ViewMatchers.withText("Register as driver"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        7
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialTextView.perform(ViewActions.click())

        val textInputEditText = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.phonenumber),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.pnumtext),
                        0
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        textInputEditText.perform(
            ViewActions.replaceText("9863841998"),
            ViewActions.closeSoftKeyboard()
        )

        val textInputEditText2 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.fullname),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.fnametext),
                        0
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        textInputEditText2.perform(
            ViewActions.replaceText("rakesh"),
            ViewActions.closeSoftKeyboard()
        )

        val textInputEditText3 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.address),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.addtext),
                        0
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        textInputEditText3.perform(
            ViewActions.replaceText("kapan"),
            ViewActions.closeSoftKeyboard()
        )

        val textInputEditText4 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.password),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.pwd),
                        0
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        textInputEditText4.perform(
            ViewActions.replaceText("123test"),
            ViewActions.closeSoftKeyboard()
        )

        val textInputEditText5 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.confirmpassword),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.passcon),
                        0
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        textInputEditText5.perform(
            ViewActions.replaceText("123test"),
            ViewActions.closeSoftKeyboard()
        )

        val appCompatImageView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.register),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        4
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatImageView.perform(ViewActions.click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

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