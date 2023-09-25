package org.bogucki.romeo.utils

import androidx.annotation.VisibleForTesting

fun String.extractSingleWords(delimiter: String): List<String> =
    this.split(delimiter)
        .map { it.lowercase().removePunctuations().trim() }
        .filter { it.isNotEmpty() }

fun String.removePunctuations(): String {
    return this.replace("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]".toRegex(), "")
}