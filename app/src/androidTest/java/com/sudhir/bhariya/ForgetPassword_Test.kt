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
class ForgetPassword_Test {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    fun forgetPassword_Test() {
        val materialTextView = onView(
allOf(withId(R.id.tvForgotpassword), withText("forgot password?"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
2),
isDisplayed()))
        materialTextView.perform(click())
        
        val appCompatEditText = onView(
allOf(withId(R.id.etPhonenumber),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
3),
isDisplayed()))
        appCompatEditText.perform(replaceText("9863841998"), closeSoftKeyboard())
        
        val materialButton = onView(
allOf(withId(R.id.btnSendcode), withText("Send Code"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
4),
isDisplayed()))
        materialButton.perform(click())
        
        val appCompatEditText2 = onView(
allOf(withId(R.id.etCode),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
2),
isDisplayed()))
        appCompatEditText2.perform(click())
        
        val materialButton2 = onView(
allOf(withId(R.id.btnContinue), withText("Continue"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
4),
isDisplayed()))
        materialButton2.perform(click())
        
        val appCompatEditText3 = onView(
allOf(withId(R.id.etConfirmpassword),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
3),
isDisplayed()))
        appCompatEditText3.perform(replaceText("123wangchhu"), closeSoftKeyboard())
        
        val appCompatEditText4 = onView(
allOf(withId(R.id.etNewpassword),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
2),
isDisplayed()))
        appCompatEditText4.perform(replaceText("123wangchhu"), closeSoftKeyboard())
        
        val materialButton3 = onView(
allOf(withId(R.id.btnContinue), withText("Continue"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
4),
isDisplayed()))
        materialButton3.perform(click())
        
        val textInputEditText = onView(
allOf(withId(R.id.etphonenumber),
childAtPosition(
childAtPosition(
withId(R.id.phonenumbertxt),
0),
0),
isDisplayed()))
        textInputEditText.perform(replaceText("9863841998"), closeSoftKeyboard())
        
        val textInputEditText2 = onView(
allOf(withId(R.id.etpassword),
childAtPosition(
childAtPosition(
withId(R.id.passwordtxt),
0),
0),
isDisplayed()))
        textInputEditText2.perform(replaceText("123wangchhu"), closeSoftKeyboard())
        
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
