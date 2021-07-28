package com.sudhir.bhariya


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
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
class Edit_Profile_Test {

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
    fun edit_Profile_Test() {
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
        textInputEditText2.perform(click())
        
        val textInputEditText3 = onView(
allOf(withId(R.id.etpassword),
childAtPosition(
childAtPosition(
withId(R.id.passwordtxt),
0),
0),
isDisplayed()))
        textInputEditText3.perform(replaceText("123@wang"), closeSoftKeyboard())
        
        val appCompatImageView = onView(
allOf(withId(R.id.btnlogin),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
4),
1),
isDisplayed()))
        appCompatImageView.perform(click())
        
        val cardView = onView(
allOf(withId(R.id.editprofile),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1),
10),
isDisplayed()))
        cardView.perform(click())
        
        val appCompatEditText = onView(
allOf(withId(R.id.etfullname), withText("wangchhu Tamang"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1),
2),
isDisplayed()))
        appCompatEditText.perform(click())
        
        val appCompatEditText2 = onView(
allOf(withId(R.id.etfullname), withText("wangchhu Tamang"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1),
2),
isDisplayed()))
        appCompatEditText2.perform(replaceText("PProfile Te"))
        
        val appCompatEditText3 = onView(
allOf(withId(R.id.etfullname), withText("PProfile Te"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1),
2),
isDisplayed()))
        appCompatEditText3.perform(closeSoftKeyboard())
        
        val appCompatEditText4 = onView(
allOf(withId(R.id.etfullname), withText("PProfile Te"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1),
2),
isDisplayed()))
        appCompatEditText4.perform(click())
        
        val appCompatEditText5 = onView(
allOf(withId(R.id.etfullname), withText("PProfile Te"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1),
2),
isDisplayed()))
        appCompatEditText5.perform(replaceText("Profile Te"))
        
        val appCompatEditText6 = onView(
allOf(withId(R.id.etfullname), withText("Profile Te"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1),
2),
isDisplayed()))
        appCompatEditText6.perform(closeSoftKeyboard())
        
        val appCompatEditText7 = onView(
allOf(withId(R.id.etfullname), withText("Profile Te"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1),
2),
isDisplayed()))
        appCompatEditText7.perform(click())
        
        val appCompatEditText8 = onView(
allOf(withId(R.id.etfullname), withText("Profile Te"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1),
2),
isDisplayed()))
        appCompatEditText8.perform(replaceText("Profile Test"))
        
        val appCompatEditText9 = onView(
allOf(withId(R.id.etfullname), withText("Profile Test"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1),
2),
isDisplayed()))
        appCompatEditText9.perform(closeSoftKeyboard())
        
        val appCompatEditText10 = onView(
allOf(withId(R.id.etaddress), withText("kapan"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1),
8),
isDisplayed()))
        appCompatEditText10.perform(replaceText("Boudha"))
        
        val appCompatEditText11 = onView(
allOf(withId(R.id.etaddress), withText("Boudha"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1),
8),
isDisplayed()))
        appCompatEditText11.perform(closeSoftKeyboard())
        
        val circleImageView = onView(
allOf(withId(R.id.userimg),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
0),
0),
isDisplayed()))
        circleImageView.perform(click())
        
        val materialTextView = onView(
allOf(withId(android.R.id.title), withText("Open Camera"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()))
        materialTextView.perform(click())
        
        val materialButton = onView(
allOf(withId(R.id.btnSave), withText("SAVE"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1),
9),
isDisplayed()))
        materialButton.perform(click())
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
