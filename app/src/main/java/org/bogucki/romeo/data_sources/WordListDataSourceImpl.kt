package org.bogucki.romeo.data_sources

import android.content.res.AssetManager
import org.bogucki.romeo.utils.extractSingleWords
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

private const val FILE_NAME = "Romeo-and-Juliet.txt"
private const val SPACE_DELIMITER = " "

class WordListDataSourceImpl @Inject constructor(private val assets: AssetManager) :
    WordListDataSource {

    override suspend fun getWordList(): List<String> {
        val builder = StringBuilder()
        val reader = BufferedReader(InputStreamReader(assets.open(FILE_NAME)))

        var line: String?

        while (reader.readLine().also { line = it } != null) {
            builder.append(line).append(SPACE_DELIMITER)
        }

        reader.close()
        val text = builder.toString()

        return text.extractSingleWords(SPACE_DELIMITER)
    }
}