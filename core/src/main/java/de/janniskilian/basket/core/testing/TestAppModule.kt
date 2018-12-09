package de.janniskilian.basket.core.testing

import android.app.Application
import androidx.room.Room
import de.janniskilian.basket.core.data.localdb.LocalDatabase
import de.janniskilian.basket.core.module.AndroidModule
import de.janniskilian.basket.core.module.AppModule
import de.janniskilian.basket.core.module.DataModule

fun createTestAppModule(application: Application): AppModule {
	val androidModule = AndroidModule(application)
	val localDatabase = Room
		.inMemoryDatabaseBuilder(application, LocalDatabase::class.java)
		.allowMainThreadQueries()
		.build()

	return AppModule(
		androidModule,
		DataModule(localDatabase)
	)
}
