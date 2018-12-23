package de.janniskilian.basket.feature.settings

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.core.content.edit
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import de.janniskilian.basket.R
import de.janniskilian.basket.core.util.function.setDayNightMode

class PreferenceFragment : PreferenceFragmentCompat() {

    private val sharedPrefs by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_screen, rootKey)

        with(get<SwitchPreferenceCompat>(R.string.pref_key_system_day_night_mode)) {
            onChange<Boolean> {
                sharedPrefs.edit {
                    putBoolean(getString(R.string.pref_key_day_night_mode), false)
                }

                updateDayNightMode(
                    it,
                    get<SwitchPreferenceCompat>(R.string.pref_key_day_night_mode).isChecked
                )
            }
        }

        with(get<SwitchPreferenceCompat>(R.string.pref_key_day_night_mode)) {
            isEnabled = !sharedPrefs.getBoolean(
                getString(R.string.pref_key_system_day_night_mode),
                resources.getBoolean(R.bool.pref_def_system_day_night_mode)
            )

            onChange<Boolean> {
                updateDayNightMode(
                    get<SwitchPreferenceCompat>(R.string.pref_key_system_day_night_mode).isChecked,
                    it
                )
            }
        }
    }

    private fun updateDayNightMode(systemDayNightMode: Boolean, dayNightMode: Boolean) {
        setDayNightMode(systemDayNightMode, dayNightMode)
        requireActivity().recreate()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Preference> get(@StringRes resId: Int): T =
        findPreference(getString(resId)) as T

    private fun <T : Any> Preference.onChange(listener: (newValue: T) -> Unit) {
        setOnPreferenceChangeListener { _, newValue ->
            @Suppress("UNCHECKED_CAST")
            listener(newValue as T)
            true
        }
    }
}
