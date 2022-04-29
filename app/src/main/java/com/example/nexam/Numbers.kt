package com.example.nexam

object Numbers {
    //TODO remove -> only for JUnit 5 test with parameter
    fun isOdd(number: Int): Boolean {
        return number % 2 != 0
    }

    fun isBlank(input: String?): Boolean {
        return input == null || input.trim { it <= ' ' }.isEmpty()
    }
}