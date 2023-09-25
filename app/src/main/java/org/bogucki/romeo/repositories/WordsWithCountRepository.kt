package org.bogucki.romeo.repositories

import org.bogucki.romeo.models.WordWithCount

interface WordsWithCountRepository {

    suspend fun getWordsWithCount(): List<WordWithCount>

    suspend fun getTotalWordCount(): Int
}