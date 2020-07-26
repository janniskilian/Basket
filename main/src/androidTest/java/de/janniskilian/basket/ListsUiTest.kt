package de.janniskilian.basket

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotExist
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickBack
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ListsUiTest : BaseUiTest() {

    @Test
    fun createAndEditAndDeleteAndRestoreList() {
        val name = "Test list"
        val newName = "Edited test list"

        clickOn(R.string.fab_create_shopping_list)
        writeTo(R.id.nameEditText, name)
        clickOn(R.string.create_list_button)

        clickBack()

        assertDisplayed(name)

        onView(allOf(withId(R.id.moreButton), hasSibling(withText(name)))).perform(click())
        clickOn(R.string.action_menu_edit_list)

        writeTo(R.id.nameEditText, newName)
        clickOn(R.string.save_list_button)

        assertDisplayed(newName)

        onView(allOf(withId(R.id.moreButton), hasSibling(withText(newName)))).perform(click())
        clickOn(R.string.action_menu_delete_list)

        assertNotExist(newName)

        clickOn(R.string.restore_button)

        assertDisplayed(newName)
    }
}
