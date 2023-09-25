package org.bogucki.romeo.usecases

import org.bogucki.romeo.repositories.WordsWithCountRepository
import javax.inject.Inject

class GetTotalWordCountUseCase @Inject constructor(private val repository: WordsWithCountRepository) {

    suspend operator fun invoke(): Int = repository.getTotalWordCount()
}