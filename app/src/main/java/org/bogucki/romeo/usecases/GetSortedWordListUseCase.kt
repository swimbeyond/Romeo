package org.bogucki.romeo.usecases

import org.bogucki.romeo.models.WordWithCount
import org.bogucki.romeo.repositories.WordsWithCountRepository
import org.bogucki.romeo.usecases.parameters.SortParameter
import javax.inject.Inject

class GetSortedWordListUseCase @Inject constructor(private val repository: WordsWithCountRepository) {

    suspend operator fun invoke(sortedBy: SortParameter): List<WordWithCount> {
        return when (sortedBy) {
            SortParameter.Alphabet -> return repository.getWordsWithCount().sortedBy { it.word }
            SortParameter.WordCount -> return repository.getWordsWithCount().sortedByDescending { it.count }
            SortParameter.WordLength -> return repository.getWordsWithCount().sortedBy { it.word.length }
        }
    }
}