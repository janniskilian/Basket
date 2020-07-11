package de.janniskilian.basket.core.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class AndroidModule(private val application: Application) {

    val applicationContext: Context
        get() = application

    val sharedPrefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }
}
