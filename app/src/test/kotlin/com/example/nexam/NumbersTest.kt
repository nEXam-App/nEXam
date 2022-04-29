package com.example.nexam

import com.example.nexam.Numbers
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class NumbersTest {

    @ParameterizedTest
    @ValueSource(ints = [1, 3, 5, -3, 15, Int.MAX_VALUE])
    fun isOdd_ShouldReturnTrueForOddNumbers(ints: Int) {
        assertTrue(Numbers.isOdd(ints))
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "  "])
    fun isBlank_ShouldReturnTrueForNullOrBlankStrings(input: String?) {
        assertTrue(Numbers.isBlank(input))
    }
}