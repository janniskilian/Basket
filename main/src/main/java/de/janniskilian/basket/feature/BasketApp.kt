package de.janniskilian.basket.feature

import android.app.Application
import android.content.SharedPreferences
import dagger.hilt.android.HiltAndroidApp
import de.janniskilian.basket.BuildConfig
import de.janniskilian.basket.R
import de.janniskilian.basket.core.util.android.setDayNightMode
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class BasketApp : Application() {

    @Inject
    lateinit var sharedPrefs: SharedPreferences

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
