package de.janniskilian.basket.core

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import de.janniskilian.basket.core.data.localdb.LocalDatabase
import de.janniskilian.basket.core.module.AndroidModule
import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.module.DataModule
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

		if (!LeakCanary.isInAnalyzerProcess(this)) {
			setupLogging()
			setupLeakCanary()
		}
	}

	private fun setupLogging() {
		if (BuildConfig.DEBUG) {
			Timber.plant(Timber.DebugTree())
		}
	}

	private fun setupLeakCanary() {
		LeakCanary.install(this)
	}
}
