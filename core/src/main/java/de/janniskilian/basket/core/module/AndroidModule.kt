package de.janniskilian.basket.core.module

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class AndroidModule(private val application: Application) {

	val applicationContext: Context
		get() = application

	val sharedPreferences: SharedPreferences by lazy {
        application.getSharedPreferences(application.packageName, MODE_PRIVATE)
    }
}
