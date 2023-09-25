package org.bogucki.romeo.repositories

import org.bogucki.romeo.data_sources.WordListDataSource
import org.bogucki.romeo.models.WordWithCount
import javax.inject.Inject

class WordsWithCountRepositoryImpl @Inject constructor(private val dataSource: WordListDataSource): WordsWithCountRepository {

    private var wordsCache: List<WordWithCount> = listOf()

    override suspend fun getWordsWithCount(): List<WordWithCount> {
        loadWordsIfNeeded()
        return wordsCache
    }

    override suspend fun getTotalWordCount(): Int {
        loadWordsIfNeeded()
        return wordsCache.sumOf { it.count }
    }

    private suspend fun loadWordsIfNeeded() {
        if (wordsCache.isEmpty())
            wordsCache = dataSource.getWordList().groupingBy { it }.eachCount().map { entry -> WordWithCount(entry.key, entry.value) }
    }
}