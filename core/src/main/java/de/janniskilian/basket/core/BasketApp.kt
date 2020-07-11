package de.janniskilian.basket.core

import android.app.Application
import de.janniskilian.basket.core.data.localdb.LocalDatabase
import de.janniskilian.basket.core.module.AndroidModule
import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.module.DataModule
import de.janniskilian.basket.core.util.function.setDayNightMode
import timber.log.Timber

class BasketApp : Application() {

	val appModule by lazy {
		val androidModule = AndroidModule(this)
		val localDatabase = LocalDatabase.create(androidModule.applicationContext)

		AppModule(
			androidModule,
			DataModule(localDatabase)
		)
	}

	override fun onCreate() {
		super.onCreate()

			setupLogging()
			setupDayNightMode()
	}

	private fun setupLogging() {
		if (BuildConfig.DEBUG) {
			Timber.plant(Timber.DebugTree())
		}
	}

	private fun setupDayNightMode() {
        val sharedPrefs = appModule.androidModule.sharedPrefs
        val autoDayNightMode = sharedPrefs.getBoolean(
            getString(R.string.pref_key_system_day_night_mode),
            resources.getBoolean(R.bool.pref_def_system_day_night_mode)
        )
        val dayNightMode = sharedPrefs.getBoolean(
            getString(R.string.pref_key_day_night_mode),
            resources.getBoolean(R.bool.pref_def_day_night_mode)
        )

        setDayNightMode(autoDayNightMode, dayNightMode)
	}
}
