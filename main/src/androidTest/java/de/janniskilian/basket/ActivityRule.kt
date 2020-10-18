package de.janniskilian.basket

import androidx.test.core.app.ActivityScenario
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import de.janniskilian.basket.feature.main.MainActivity

class ActivityRule(
    private val isSkipOnboarding: Boolean
) : ActivityScenario<MainActivity>(MainActivity::class.java) {

    override fun onActivity(action: ActivityAction<MainActivity>?): ActivityScenario<MainActivity> {
        action?.
    }

    override fun afterActivityLaunched() {
        if (isSkipOnboarding) {
            clickOn(R.id.button)
        }
    }
}
