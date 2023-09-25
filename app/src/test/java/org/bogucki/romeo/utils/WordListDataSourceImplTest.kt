package org.bogucki.romeo.utils

import org.junit.Assert.*
import org.junit.Test

class StringExtensionsTest {

    @Test
    fun `verify that an empty string is split into an empty word list`() {
        val input = ""
        assertEquals(listOf<String>(), input.extractSingleWords(" "))
    }

    @Test
    fun `verify that non-empty is split into word list`() {
        val input = " abc def Ghe, xyz!  kLm: ! (opq) abc_ :;"
        val output = listOf(
            "abc", "def", "ghe", "xyz", "klm", "opq", "abc"
        )
        assertEquals(output, input.extractSingleWords(" "))
    }

    @Test
    fun `verify that punctuation is removed correctly`() {
        var input = ".;("
        assertEquals("", input.removePunctuations())
        input = "abc"
        assertEquals("abc", input.removePunctuations())
        input = "(abc"
        assertEquals("abc", input.removePunctuations())
        input = "ab.c"
        assertEquals("abc", input.removePunctuations())
        input = "ab.c!"
        assertEquals("abc", input.removePunctuations())
    }
}