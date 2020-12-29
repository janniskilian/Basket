package de.janniskilian.basket.feature.lists

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ListsModule {

    @Singleton
    @Provides
    fun provideShortcutController(@ApplicationContext context: Context): ShortcutController =
        ShortcutController(context)
}
