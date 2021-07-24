package com.sudhir.bhariya


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
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
class Dashboard_ActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    fun dashboard_ActivityTest() {
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
allOf(withId(R.id.ic_setting), withContentDescription("Setting"),
childAtPosition(
childAtPosition(
withId(R.id.botton_nav),
0),
3),
isDisplayed()))
        bottomNavigationItemView.perform(click())
        
        val bottomNavigationItemView2 = onView(
allOf(withId(R.id.ic_dashboard), withContentDescription("Dashboard"),
childAtPosition(
childAtPosition(
withId(R.id.botton_nav),
0),
0),
isDisplayed()))
        bottomNavigationItemView2.perform(click())
        
        val bottomNavigationItemView3 = onView(
allOf(withId(R.id.ic_notification), withContentDescription("Notification"),
childAtPosition(
childAtPosition(
withId(R.id.botton_nav),
0),
2),
isDisplayed()))
        bottomNavigationItemView3.perform(click())
        
        val bottomNavigationItemView4 = onView(
allOf(withId(R.id.ic_history), withContentDescription("History"),
childAtPosition(
childAtPosition(
withId(R.id.botton_nav),
0),
1),
isDisplayed()))
        bottomNavigationItemView4.perform(click())
        
        val bottomNavigationItemView5 = onView(
allOf(withId(R.id.ic_dashboard), withContentDescription("Dashboard"),
childAtPosition(
childAtPosition(
withId(R.id.botton_nav),
0),
0),
isDisplayed()))
        bottomNavigationItemView5.perform(click())
        
        val materialButton = onView(
allOf(withText("Vehicle"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
3),
1)))
        materialButton.perform(scrollTo(), click())
        
        pressBack()
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
