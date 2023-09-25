package org.bogucki.romeo.repositories

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.bogucki.romeo.data_sources.WordListDataSource
import org.bogucki.romeo.models.WordWithCount
import org.junit.Assert.*
import org.junit.Test

class WordsWithCountRepositoryImplTest {

    private val dataSource: WordListDataSource = mockk()

    private val repository = WordsWithCountRepositoryImpl(dataSource)

    @Test
    fun `given that dataSource returns empty list verify that wordsWithCount is empty`() = runTest {
        coEvery { dataSource.getWordList() } returns listOf()
        assertTrue(repository.getWordsWithCount().isEmpty())
    }

    @Test
    fun `given that dataSource returns empty list verify that total word count is 0`() = runTest {
        coEvery { dataSource.getWordList() } returns listOf()
        assertEquals(0, repository.getTotalWordCount())
    }

    @Test
    fun `given that dataSource returns non-empty word list verify that getWordsWithCount returns correct data`() = runTest {
        coEvery { dataSource.getWordList() } returns listOf("a", "b", "ab", "abc", "d", "d", "a", "abc")
        assertEquals(5, repository.getWordsWithCount().size)
        assertTrue(repository.getWordsWithCount().contains(WordWithCount("a", 2)))
        assertTrue(repository.getWordsWithCount().contains(WordWithCount("abc", 2)))
        assertTrue(repository.getWordsWithCount().contains(WordWithCount("d", 2)))
        assertTrue(repository.getWordsWithCount().contains(WordWithCount("b", 1)))
    }

    @Test
    fun `given that dataSource returns non-empty word list verify that getTotalWordCount returns correct value`() = runTest {
        coEvery { dataSource.getWordList() } returns listOf("a", "b", "ab", "abc", "d", "d", "a", "abc")
        assertEquals(8, repository.getTotalWordCount())
    }
}