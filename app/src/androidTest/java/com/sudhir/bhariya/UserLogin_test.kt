package com.sudhir.bhariya

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@LargeTest
@RunWith(JUnit4::class)
class UserLogin_test {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun DriverLogin_Test() {
        Espresso.onView(ViewMatchers.withId(R.id.etphonenumber))
            .perform(ViewActions.typeText("9813910902"))
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.etpassword))
            .perform(ViewActions.typeText("123@wang"))
        Thread.sleep(1000)
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.btnlogin)).perform(ViewActions.click())
        Thread.sleep(4000)
        Espresso.onView(ViewMatchers.withId(R.id.test))
            .check(ViewAssertions.matches(ViewMatchers.withText("test")))

    }
}