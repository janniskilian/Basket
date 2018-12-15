package de.janniskilian.basket

import androidx.core.content.edit
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.ActivityTestRule
import de.janniskilian.basket.core.BasketApp
import de.janniskilian.basket.feature.main.MainActivity

class UiTestRule : ActivityTestRule<MainActivity>(MainActivity::class.java) {

	override fun beforeActivityLaunched() {
		super.beforeActivityLaunched()

		val app = ApplicationProvider.getApplicationContext<BasketApp>()
		val appModule = app.appModule

		appModule
			.dataModule
			.dataClient
			.clear()

		appModule.androidModule.sharedPreferences.edit {
			clear()
		}
	}
}
