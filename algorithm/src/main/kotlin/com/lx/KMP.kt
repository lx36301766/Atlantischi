package com.lx

/**
 * Created on 2018/12/19.

 * @author lx
 *
 *
 * https://www.zhihu.com/question/21923021
 *
 */


fun getNext(pattern: String, next: IntArray) {
    var j = 0
    var k = -1
    val len = pattern.length
    next[0] = -1
    while (j < len - 1) {
        if (k == -1 || pattern[k] == pattern[j]) {
            j++
            k++
            next[j] = k
        } else {
            // 比较到第K个字符，说明p[0——k-1]字符串和p[j-k——j-1]字符串相等，而next[k]表示
            // p[0——k-1]的前缀和后缀的最长共有长度，所接下来可以直接比较p[next[k]]和p[j]
            k = next[k]
        }
    }
}

fun kmp(s: String, pattern: String): Int {
    var i = 0
    var j = 0
    val slen = s.length
    val plen = pattern.length
    val next = IntArray(plen)
    getNext(pattern, next)
    while (i < slen && j < plen) {
        if (s[i] == pattern[j]) {
            i++
            j++
        } else {
            if (next[j] == -1) {
                i++
                j = 0
            } else {
                j = next[j]
            }
        }
        if (j == plen) {
            return i - j
        }
    }
    return -1
}
