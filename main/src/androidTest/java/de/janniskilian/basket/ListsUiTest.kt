package de.janniskilian.basket

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ListsUiTest {

    @get:Rule
    val activityRule = UiTestRule()

    @Test
    fun createAndEditAndDeleteAndRestoreList() {
        val name = "Test list"
        val newName = "Edited test list"

        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.nameEditText)).perform(typeText(name))
        closeSoftKeyboard()
        onView(withId(R.id.createButton)).perform(click())

        onView(withText(name)).check(matches(isDisplayed()))

        onView(allOf(withId(R.id.moreButton), hasSibling(withText(name)))).perform(click())
        onView(withText(R.string.action_menu_edit_list)).perform(click())

        onView(withId(R.id.nameEditText)).perform(replaceText(newName))
        closeSoftKeyboard()
        onView(withId(R.id.createButton)).perform(click())

        onView(withText(newName)).check(matches(isDisplayed()))

        onView(allOf(withId(R.id.moreButton), hasSibling(withText(newName)))).perform(click())
        onView(withText(R.string.action_menu_delete_list)).perform(click())

        onView(withText(newName)).check(doesNotExist())

        onView(withText(R.string.restore_button)).perform(click())

        onView(withText(newName)).check(matches(isDisplayed()))
    }
}
