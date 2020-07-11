package de.janniskilian.basket

import androidx.core.content.edit
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import de.janniskilian.basket.core.BasketApp
import de.janniskilian.basket.feature.main.MainActivity

class UiTestRule(
    private val skipOnboarding: Boolean = true
) : ActivityTestRule<MainActivity>(MainActivity::class.java) {

    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()

        val app = ApplicationProvider.getApplicationContext<BasketApp>()
        val appModule = app.appModule

        appModule
            .dataModule
            .dataClient
            .clear()

        appModule.androidModule.sharedPrefs.edit {
            clear()
        }
    }

    override fun afterActivityLaunched() {
        if (skipOnboarding) {
            onView(withId(R.id.button)).perform(click())
        }
    }
}
