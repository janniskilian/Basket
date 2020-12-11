package de.janniskilian.basket.feature.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AndroidModule {

    @Singleton
    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    @Singleton
    @Provides
    fun provideDefaultPreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> =
        context.createDataStore(name = DEFAULT_PREFERENCES_DATA_STORE_NAME)

    companion object {

        private const val DEFAULT_PREFERENCES_DATA_STORE_NAME = "default"
    }
}
