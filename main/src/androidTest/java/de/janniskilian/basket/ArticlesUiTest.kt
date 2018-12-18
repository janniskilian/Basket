package de.janniskilian.basket

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import de.janniskilian.basket.core.CategoriesAdapter
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ArticlesUiTest {

    @get:Rule
    val activityRule = UiTestRule()

    @Test
    fun createAndEditAndDeleteArticle() {
        val name = "Test article"
        val editedName = "Edited test article"

        createArticle(name)
        editArticle(name, editedName)
        deleteArticle(editedName)

        findArticleItem(editedName)
        getArticleItemViewInteraction(editedName).check(doesNotExist())
    }

    private fun createArticle(name: String) {
        onView(withId(R.id.navigationButton)).perform(click())
        onView(withText(R.string.navigation_articles)).perform(click())

        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.nameEditText)).perform(typeText(name))
        closeSoftKeyboard()
        onView(withId(R.id.categoryEditText)).perform(click())
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CategoriesAdapter.ViewHolder>(1, click())
        )
        onView(withId(R.id.submitButton)).perform(click())
    }

    private fun editArticle(name: String, editedName: String) {
        findArticleItem(name)
        assertArticleIsDisplayed(name)

        getArticleItemViewInteraction(name).perform(click())

        onView(withId(R.id.nameEditText)).perform(replaceText(editedName))
        closeSoftKeyboard()
        onView(withId(R.id.categoryEditText)).perform(click())
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CategoriesAdapter.ViewHolder>(0, click())
        )
        onView(withId(R.id.submitButton)).perform(click())
    }

    private fun deleteArticle(name: String) {
        findArticleItem(name)
        assertArticleIsDisplayed(name)

        getArticleItemViewInteraction(name).perform(click())

        onView(withId(R.id.deleteButton)).perform(click())
    }

    private fun findArticleItem(name: String) {
        onView(withId(R.id.action_search)).perform(click())
        onView(withId(R.id.searchBarEditText)).perform(typeText(name))
    }

    private fun getArticleItemViewInteraction(name: String) =
        onView(allOf(withChild(withText(name)), isDescendantOfA(withId(R.id.recyclerView))))

    private fun assertArticleIsDisplayed(editedName: String) {
        getArticleItemViewInteraction(editedName).check(matches(isDisplayed()))
    }
}
