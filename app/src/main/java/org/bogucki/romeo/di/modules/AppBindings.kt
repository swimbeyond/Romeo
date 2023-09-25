package org.bogucki.romeo.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.bogucki.romeo.data_sources.WordListDataSource
import org.bogucki.romeo.data_sources.WordListDataSourceImpl
import org.bogucki.romeo.repositories.WordsWithCountRepository
import org.bogucki.romeo.repositories.WordsWithCountRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindings {

    @Binds
    abstract fun bindDataSource(wordListDataSource: WordListDataSourceImpl): WordListDataSource

    @Binds
    abstract fun bindRepository(wordsWithCountRepository: WordsWithCountRepositoryImpl): WordsWithCountRepository
}