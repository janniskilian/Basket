package de.janniskilian.basket.feature.articles.article

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import de.janniskilian.basket.core.data.DataClient

@InstallIn(ActivityRetainedComponent::class)
@Module
class ArticleModule {

    @Provides
    fun provideArticleFragmentUseCases(dataClient: DataClient): ArticleFragmentUseCases =
        ArticleFragmentUseCases(dataClient)
}
