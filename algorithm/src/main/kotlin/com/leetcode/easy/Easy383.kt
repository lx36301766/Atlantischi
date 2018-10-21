package com.leetcode.easy

/**

    Given an arbitrary ransom note string and another string containing letters from all the magazines,
    write a function that will return true if the ransom note can be constructed from the magazines ; otherwise, it will return false.

    Each letter in the magazine string can only be used once in your ransom note.

    Note:
    You may assume that both strings contain only lowercase letters.

    canConstruct("a", "b") -> false
    canConstruct("aa", "ab") -> false
    canConstruct("aa", "aab") -> true

 */


fun canConstruct(ransomNote: String, magazine: String): Boolean {
    val magazineLetterCount = IntArray(26)
    for (char in magazine) {
        magazineLetterCount[char - 'a']++
    }
    for (char in ransomNote) {
        if (--magazineLetterCount[char - 'a'] < 0) {
            return false
        }
    }
    return true
}

fun main(args: Array<String>) {
    println(canConstruct("cabaabbc", "aaabbbccc"))
    println(canConstruct("cababbc", "aaabbbc"))
}
