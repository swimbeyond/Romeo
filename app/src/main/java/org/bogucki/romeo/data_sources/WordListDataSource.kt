package org.bogucki.romeo.data_sources

interface WordListDataSource {

    suspend fun getWordList(): List<String>
}