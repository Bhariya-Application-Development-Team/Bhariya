package com.sudhir.bhariya


import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
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
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.math.sign

@LargeTest
@RunWith(JUnit4::class)
class UserLogin_Test {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun userLogin_Test() {
        onView(withId(R.id.etphonenumber)).perform(typeText("9813910902"))
        Thread.sleep(1000)
        onView(withId(R.id.etpassword)).perform(typeText("123@wang"))
        Thread.sleep(1000)
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btnlogin)).perform(click())
        Thread.sleep(4000)
        onView(withId(R.id.test)).check(ViewAssertions.matches(withText("test")))

        }

    }
