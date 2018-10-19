package com.leetcode.hard

/**

    Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.

    Example 1:

    Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
    Output: true
    Example 2:

    Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
    Output: false

    Input: "aabcc", "dbbca", "aadbcbbcac"
    Output: true


    Solution: https://leetcode.com/articles/interleaving-strings/

 */


// Approach 1: Brute Force
// Time complexity : O(2^{m+n})
// Space complexity : O(m+n)
fun is_Interleave(s1: String, i: Int, s2: String, j: Int, res: String, s3: String): Boolean {
    if (res == s3 && i == s1.length && j == s2.length)
        return true
    var ans = false
    if (i < s1.length)
        ans = ans or is_Interleave(s1, i + 1, s2, j, res + s1[i], s3)
    if (j < s2.length)
        ans = ans or is_Interleave(s1, i, s2, j + 1, res + s2[j], s3)
    return ans

}
fun isInterleave(s1: String, s2: String, s3: String): Boolean {
    return is_Interleave(s1, 0, s2, 0, "", s3)
}


// Approach 2: Recursion with memoization
// Time complexity : O(2^{m+n})
// Space complexity : O(m+n)
fun is_Interleave2(s1: String, i: Int, s2: String, j: Int, s3: String, k: Int, memo: Array<IntArray>): Boolean {
    if (i == s1.length) {
        return s2.substring(j) == s3.substring(k)
    }
    if (j == s2.length) {
        return s1.substring(i) == s3.substring(k)
    }
    if (memo[i][j] >= 0) {
        return memo[i][j] == 1
    }
    var ans = false
    if (s3[k] == s1[i] && is_Interleave2(s1, i + 1, s2, j, s3, k + 1, memo)
            || s3[k] == s2[j] && is_Interleave2(s1, i, s2, j + 1, s3, k + 1, memo)) {
        ans = true
    }
    memo[i][j] = if (ans) 1 else 0
    return ans
}
fun isInterleave2(s1: String, s2: String, s3: String): Boolean {
    val memo = Array(s1.length) { IntArray(s2.length) }
    for (i in 0 until s1.length) {
        for (j in 0 until s2.length) {
            memo[i][j] = -1
        }
    }
    return is_Interleave2(s1, 0, s2, 0, s3, 0, memo)
}


// Approach 3: Using 2D Dynamic Programming
// Time complexity : O(m*n)
// Space complexity : O(m*n)
fun isInterleave3(s1: String, s2: String, s3: String): Boolean {
    if (s3.length != s1.length + s2.length) {
        return false
    }
    val dp = Array(s1.length + 1) { BooleanArray(s2.length + 1) }
    for (i in 0..s1.length) {
        for (j in 0..s2.length) {
            if (i == 0 && j == 0) {
                dp[i][j] = true
            } else if (i == 0) {
                dp[i][j] = dp[i][j - 1] && s2[j - 1] == s3[i + j - 1]
            } else if (j == 0) {
                dp[i][j] = dp[i - 1][j] && s1[i - 1] == s3[i + j - 1]
            } else {
                dp[i][j] = dp[i - 1][j] && s1[i - 1] == s3[i + j - 1] || dp[i][j - 1] && s2[j - 1] == s3[i + j - 1]
            }
        }
    }
    return dp[s1.length][s2.length]
}


// Approach 4: Using 1D Dynamic Programming
// Time complexity : O(m*n)
// Space complexity : O(n)
fun isInterleave4(s1: String, s2: String, s3: String): Boolean {
    if (s3.length != s1.length + s2.length) {
        return false
    }
    val dp = BooleanArray(s2.length + 1)
    for (i in 0..s1.length) {
        for (j in 0..s2.length) {
            if (i == 0 && j == 0) {
                dp[j] = true
            } else if (i == 0) {
                dp[j] = dp[j - 1] && s2[j - 1] == s3[i + j - 1]
            } else if (j == 0) {
                dp[j] = dp[j] && s1[i - 1] == s3[i + j - 1]
            } else {
                dp[j] = dp[j] && s1[i - 1] == s3[i + j - 1] || dp[j - 1] && s2[j - 1] == s3[i + j - 1]
            }
        }
    }
    return dp[s2.length]
}


fun main(args: Array<String>) {
    println(isInterleave3("aabcc", "dbbca","aadbbcbcac"))
    println(isInterleave3("aabcc", "11223","aa11b2c33c"))
    println(isInterleave3("c", "ca","cac"))
    println(isInterleave3("droi", "android","androiddroi"))
    println(isInterleave3("droi", "android","anddrroidoi"))
    println(isInterleave3("droic", "androidca","anddrroidoicac"))
    println(isInterleave3("a", "b","aba"))
    println(isInterleave3("aabcc", "dbbca","aadbcbbcac"))
    println(isInterleave3("aab", "ab","aabab"))
}
