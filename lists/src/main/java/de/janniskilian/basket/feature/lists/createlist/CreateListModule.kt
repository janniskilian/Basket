package de.janniskilian.basket.feature.lists.createlist

import android.content.Context
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import de.janniskilian.basket.core.data.DataClient
import de.janniskilian.basket.feature.lists.R

@InstallIn(ActivityRetainedComponent::class)
@Module
class CreateListModule {

    @Provides
    fun provideCreateListFragmentUseCases(dataClient: DataClient) =
        CreateListFragmentUseCases(dataClient)

    @Provides
    fun provideColors(@ApplicationContext context: Context): List<Int> =
        listOf(
            R.color.orange,
            R.color.red,
            R.color.pink,
            R.color.purple,
            R.color.indigo,
            R.color.cyan,
            R.color.green,
            R.color.blue_grey,
            R.color.brown,
            R.color.grey
        ).map {
            ContextCompat.getColor(context, it)
        }
}
