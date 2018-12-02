package de.janniskilian.basket.core.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class AndroidModule(private val application: Application) {

	val applicationContext: Context
		get() = application

	val sharedPreferences: SharedPreferences by lazy {
		PreferenceManager.getDefaultSharedPreferences(applicationContext)
	}
}
