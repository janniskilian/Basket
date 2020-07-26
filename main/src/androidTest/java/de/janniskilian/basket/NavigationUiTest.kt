package de.janniskilian.basket

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.schibsted.spain.barista.assertion.BaristaAssertions.assertThatBackButtonClosesTheApp
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class NavigationUiTest : BaseUiTest() {

    @Test
    fun shouldStartBottomNavigationDrawerDestinationsAsNavigationRoot() {
        navigateToBottomNavigationDrawerDestination(R.string.groups_title)
        assertDisplayed(R.string.groups_title)

        navigateToBottomNavigationDrawerDestination(R.string.articles_title)
        assertDisplayed(R.string.articles_title)

        navigateToBottomNavigationDrawerDestination(R.string.categories_title)
        assertDisplayed(R.string.categories_title)

        navigateToBottomNavigationDrawerDestination(R.string.settings_title)
        assertDisplayed(R.string.settings_title)

        navigateToBottomNavigationDrawerDestination(R.string.shopping_lists_title)
        assertDisplayed(R.string.shopping_lists_title)

        assertThatBackButtonClosesTheApp()
    }
}
