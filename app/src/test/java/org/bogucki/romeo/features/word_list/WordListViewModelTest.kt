package org.bogucki.romeo.features.word_list

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.bogucki.calllog.MainDispatcherRule
import org.bogucki.romeo.models.WordWithCount
import org.bogucki.romeo.usecases.GetSortedWordListUseCase
import org.bogucki.romeo.usecases.GetTotalWordCountUseCase
import org.bogucki.romeo.usecases.parameters.SortParameter
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WordListViewModelTest {

    @get:Rule
    val coroutineRule = MainDispatcherRule()

    private val getSortedWordList: GetSortedWordListUseCase =
        mockk<GetSortedWordListUseCase>().also {
            coEvery { it.invoke(any()) } coAnswers {
                delay(500)
                listOf(
                    WordWithCount("a", 1),
                    WordWithCount("b", 2),
                    WordWithCount("c", 1),
                )
            }
        }
    private val getTotalWordCount: GetTotalWordCountUseCase =
        mockk<GetTotalWordCountUseCase>().also {
            coEvery { it.invoke() } returns 4
        }

    private lateinit var viewModel: WordListViewModel

    @Before
    fun SetUp() {
        viewModel = WordListViewModel(getSortedWordList, getTotalWordCount)
    }

    @Test
    fun `verify that loading sorted data generates correct state`() = runTest {

        viewModel.state.test {
            //the state pre-loaded at init
            awaitItem()

            viewModel.loadSortedList(SortParameter.Alphabet)
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)
            assertTrue(loadingState.wordsWithCount.isEmpty())
            assertEquals(SortParameter.Alphabet, loadingState.orderedBy)

            val loadedState = awaitItem()
            assertFalse(loadedState.isLoading)
            assertEquals(3, loadedState.wordsWithCount.size)
        }
    }

    @Test
    fun `verify that viewModel pre-loads initial data at start`() = runTest {

        viewModel.state.test {
            assert(awaitItem().isLoading)
            assertEquals(3, awaitItem().wordsWithCount.size)
        }
    }
}