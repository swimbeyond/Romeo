package org.bogucki.romeo.usecases.parameters

sealed class SortParameter(val displayName: String) {
        object WordCount : SortParameter("Word Frequency")
        object WordLength : SortParameter("Word Length")
        object Alphabet : SortParameter("Alphabetically")
    }