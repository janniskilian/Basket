package de.janniskilian.basket.feature.categories.category

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import de.janniskilian.basket.core.data.DataClient

@InstallIn(ActivityRetainedComponent::class)
@Module
class CategoryModule {

    @Provides
    fun provideCategoryFragmentUseCases(dataClient: DataClient): CategoryFragmentUseCases =
        CategoryFragmentUseCases(dataClient)
}
