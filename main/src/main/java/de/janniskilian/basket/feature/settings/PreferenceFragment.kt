package de.janniskilian.basket.feature.settings

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import de.janniskilian.basket.R
import de.janniskilian.basket.core.util.extension.extern.appModule
import de.janniskilian.basket.core.util.function.getBoolean
import de.janniskilian.basket.core.util.function.setDayNightMode

class PreferenceFragment : PreferenceFragmentCompat() {

    private val sharedPrefs get() = appModule.androidModule.sharedPrefs

    private val systemDayNightModeSwitch
        get() = get<SwitchPreferenceCompat>(R.string.pref_key_system_day_night_mode)

    private val dayNightModeSwitch
        get() = get<SwitchPreferenceCompat>(R.string.pref_key_day_night_mode)

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_screen, rootKey)

        systemDayNightModeSwitch?.apply {
            onChange<Boolean> {
                val dayNightMode = dayNightModeSwitch?.isChecked
                    ?: getBoolean(context, R.bool.pref_def_day_night_mode)

                updateDayNightMode(it, dayNightMode)

                dayNightModeSwitch?.isEnabled = !it
            }
        }

        dayNightModeSwitch?.apply {
            isEnabled = !sharedPrefs.getBoolean(
                getString(R.string.pref_key_system_day_night_mode),
                resources.getBoolean(R.bool.pref_def_system_day_night_mode)
            )

            onChange<Boolean> {
                val systemDayNightMode = systemDayNightModeSwitch?.isChecked
                    ?: getBoolean(context, R.bool.pref_def_system_day_night_mode)

                updateDayNightMode(systemDayNightMode, it)
            }
        }
    }

    private fun updateDayNightMode(systemDayNightMode: Boolean, dayNightMode: Boolean) {
        setDayNightMode(systemDayNightMode, dayNightMode)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Preference> get(@StringRes resId: Int): T? =
        findPreference(getString(resId))

    private fun <T : Any> Preference.onChange(listener: (newValue: T) -> Unit) {
        setOnPreferenceChangeListener { _, newValue ->
            @Suppress("UNCHECKED_CAST")
            listener(newValue as T)
            true
        }
    }
}
