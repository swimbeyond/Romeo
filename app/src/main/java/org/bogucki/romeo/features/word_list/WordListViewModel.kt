package org.bogucki.romeo.features.word_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bogucki.romeo.models.WordWithCount
import org.bogucki.romeo.usecases.GetSortedWordListUseCase
import org.bogucki.romeo.usecases.GetTotalWordCountUseCase
import org.bogucki.romeo.usecases.parameters.SortParameter
import javax.inject.Inject

@HiltViewModel
class WordListViewModel @Inject constructor(
    private val getSortedWordList: GetSortedWordListUseCase,
    private val getTotalWordCount: GetTotalWordCountUseCase
) :
    ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    init {
        loadSortedList(_state.value.orderedBy)
    }

    fun loadSortedList(parameter: SortParameter) =
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(isLoading = true, orderedBy = parameter)
            }
            _state.update {
                it.copy(
                    wordsWithCount = getSortedWordList(parameter),
                    totalCount = getTotalWordCount(),
                    isLoading = false
                )
            }
        }

    data class UiState(
        val isLoading: Boolean = false,
        val wordsWithCount: List<WordWithCount> = listOf(),
        val orderedBy: SortParameter = SortParameter.WordCount,
        val sortOptions: List<SortParameter> = listOf(
            SortParameter.WordCount,
            SortParameter.Alphabet,
            SortParameter.WordLength
        ),
        val totalCount: Int = 0
    )
}