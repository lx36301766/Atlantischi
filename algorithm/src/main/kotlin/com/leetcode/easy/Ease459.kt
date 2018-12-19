package com.leetcode.easy

/**


    给定一个非空的字符串，判断它是否可以由它的一个子串重复多次构成。给定的字符串只含有小写英文字母，并且长度不超过10000。

    示例 1:

    输入: "abab"

    输出: True

    解释: 可由子字符串 "ab" 重复两次构成。
    示例 2:

    输入: "aba"

    输出: False
    示例 3:

    输入: "abcabcabcabc"

    输出: True

    解释: 可由子字符串 "abc" 重复四次构成。 (或者子字符串 "abcabc" 重复两次构成。)


 */


/**
 * 暴力法，从中间开始遍历
 */
fun repeatedSubstringPattern(str: String): Boolean {
    val length = str.length
    if (length < 2) {
        return false
    }
    for (i in length / 2 until length) {
        val subLength = length - i
        if (length % subLength == 0) {
            val repeatCount = length / subLength
            val subStr = str.substring(i)
            val builder = StringBuilder()
            for (j in 0 until repeatCount) {
                builder.append(subStr)
            }
            if (builder.toString() == str) {
                return true
            }
        }
    }
    return false
}


/**
 *  原字符串double拼接，去掉首尾字母，然后在剩余字符串中查找是否包含原字符串
 */
fun repeatedSubstringPattern2(str: String) = str.length > 1 && (str + str).substring(1, str.length * 2 - 1).contains(str)


// kmp
// TODO   Do not understand
fun repeatedSubstringPattern3(s: String): Boolean {
    val n = s.length
    val next = getNext(s)
    val len = n - next[n - 1]
    if (n == len || n % len != 0) return false
    val t = s.substring(0, len)
    var i = 0
    while (i + len <= n) {
        val sub = s.substring(i, i + len)
        if (sub != t) return false
        i += len
    }
    return true
}

private fun getNext(s: String): IntArray {
    val len = s.length
    val next = IntArray(len)
    for (i in 1 until len) {
        var j = next[i - 1]
        while (j != 0 && s[i] != s[j]) {
            j = next[j - 1]
        }
        if (s[i] == s[j]) {
            next[i] = j + 1
        } else {
            next[i] = 0
        }
    }
    println(next.contentToString())
    return next
}

fun main(args: Array<String>) {

    println(repeatedSubstringPattern("bb"))
    println(repeatedSubstringPattern("abababca"))
    println(repeatedSubstringPattern("absdsx"))
    println(repeatedSubstringPattern("abcdabcdabcd"))

//    while (true) {
//        val scanner = Scanner(System.`in`)
//        println(repeatedSubstringPattern(scanner.nextLine()))
//    }

}
