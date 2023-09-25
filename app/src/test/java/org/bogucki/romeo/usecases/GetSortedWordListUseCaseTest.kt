package org.bogucki.romeo.usecases

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.bogucki.romeo.models.WordWithCount
import org.bogucki.romeo.repositories.WordsWithCountRepository
import org.bogucki.romeo.usecases.parameters.SortParameter
import org.junit.Test

class GetSortedWordListUseCaseTest {

    private val repository: WordsWithCountRepository = mockk()

    private val useCase = GetSortedWordListUseCase(repository)

    @Test
    fun `verify that words are sorted by frequency correctly`()  = runTest {
        coEvery { repository.getWordsWithCount() } returns inputMock

        val sorted = useCase.invoke(SortParameter.WordCount)

        sorted.forEachIndexed { index, item ->
            if (index < sorted.size - 1)
            assert(item.count >= sorted[index+1].count)
        }
    }

    @Test
    fun `verify that words are sorted by word length correctly`()  = runTest {
        coEvery { repository.getWordsWithCount() } returns inputMock

        val sorted = useCase.invoke(SortParameter.WordLength)

        sorted.forEachIndexed { index, item ->
            if (index < sorted.size - 1)
                assert(item.word.length <= sorted[index+1].word.length)
        }
    }

    @Test
    fun `verify that words are sorted alphabetically correctly`()  = runTest {
        coEvery { repository.getWordsWithCount() } returns inputMock

        val sorted = useCase.invoke(SortParameter.Alphabet)

        sorted.forEachIndexed { index, item ->
            if (index < sorted.size - 1)
                assert(item.word < sorted[index+1].word)
        }
    }

}

private val inputMock = listOf(
    WordWithCount("a", 1),
    WordWithCount("b", 1),
    WordWithCount("c", 2),
    WordWithCount("abc", 5),
    WordWithCount("abcdef", 3),
    WordWithCount("e", 16)
)