package com.sudhir.bhariya

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class DriverLogin_Test {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(DriverLoginActivity::class.java)

    @Test
    fun DriverLogin_Test() {
        onView(withId(R.id.etphonenumber)).perform(typeText("9863841998"))
        Thread.sleep(1000)
        onView(withId(R.id.etpassword)).perform(typeText("123test"))
        Thread.sleep(1000)
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btnlogin)).perform(click())
        Thread.sleep(4000)
        onView(withId(R.id.test)).check(ViewAssertions.matches(withText("test")))

    }
}