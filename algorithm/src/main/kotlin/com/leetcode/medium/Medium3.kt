package com.leetcode.medium

/**

    给定一个字符串，找出不含有重复字符的最长子串的长度。

    示例 1:

    输入: "abcabcbb"
    输出: 3
    解释: 无重复字符的最长子串是 "abc"，其长度为 3。
    示例 2:

    输入: "bbbbb"
    输出: 1
    解释: 无重复字符的最长子串是 "b"，其长度为 1。
    示例 3:

    输入: "pwwkew"
    输出: 3
    解释: 无重复字符的最长子串是 "wke"，其长度为 3。
    请注意，答案必须是一个子串，"pwke" 是一个子序列 而不是子串。


    Solution: https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/solution/

 */

// 方法1：暴力法
// 时间复杂度: O(n^3)
// 空间复杂度：O(min(n, m))
fun lengthOfLongestSubstring(str: String): Int {
    var length = 0
    for (start in str.indices) {
        var tmpStr = ""
        for (index in start until str.length) {
            val v = str[index]
            if (tmpStr.contains(v)) {
                length = Math.max(length, tmpStr.length)
                break
            } else {
                tmpStr += v
                length = Math.max(length, tmpStr.length)
            }
        }
    }
    return length
}

// 方法2：滑动窗口
fun lengthOfLongestSubstring2(str: String): Int {
    var length = 0
    var tmpStr = ""
    var v: Char
    for (index in str.indices) {
        v = str[index]
        if (tmpStr.contains(v)) {
            tmpStr = tmpStr.substringAfter(v) + v
        } else {
            tmpStr += v
            length = Math.max(length, tmpStr.length)
        }
    }
    return length
}


// 方法3：优化滑动窗口
//
// 以前的我们都没有对字符串 s 所使用的字符集进行假设。
//
// 当我们知道该字符集比较小的时侯，我们可以用一个整数数组作为直接访问表来替换 Map。
//
// 常用的表如下所示：
//
// int [26] 用于字母 ‘a’ - ‘z’或 ‘A’ - ‘Z’
// int [128] 用于ASCII码
// int [256] 用于扩展ASCII码
fun lengthOfLongestSubstring3(s: String): Int {
    val n = s.length
    var ans = 0
    val index = IntArray(128) // current index of character
    // try to extend the range [i, j]
    var j = 0
    var i = 0
    while (j < n) {
        i = Math.max(index[s[j].toInt()], i)
        ans = Math.max(ans, j - i + 1)
        index[s[j].toInt()] = j + 1
        j++
    }
    return ans
}

fun main(args: Array<String>) {
//    println(lengthOfLongestSubstring2("abcabcbb"))
//    println(lengthOfLongestSubstring2("bbbbb"))
    println(lengthOfLongestSubstring3("pwwkewerfvssibvsgkhyeqm"))
}

