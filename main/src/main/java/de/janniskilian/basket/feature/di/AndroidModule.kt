package de.janniskilian.basket.feature.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import de.janniskilian.basket.feature.lists.lists.ShortcutController
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class AndroidModule {

    @Singleton
    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    @Singleton
    @Provides
    fun provideDefaultDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.createDataStore(name = "default")

    @Singleton
    @Provides
    fun provideShortcutController(@ApplicationContext context: Context): ShortcutController =
        ShortcutController(context)
}
