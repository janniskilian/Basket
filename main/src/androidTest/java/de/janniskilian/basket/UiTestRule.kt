package de.janniskilian.basket

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.ActivityTestRule
import de.janniskilian.basket.feature.main.MainActivity

class UiTestRule : ActivityTestRule<MainActivity>(MainActivity::class.java) {

	override fun beforeActivityLaunched() {
		super.beforeActivityLaunched()

		ApplicationProvider
			.getApplicationContext<Context>()
			.dataDir
			.listFiles()
			.forEach { it.deleteRecursively() }
	}
}
