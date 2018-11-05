package com.leetcode.hard

/**


    Given an array A, we may rotate it by a non-negative integer K so that the array becomes A[K], A[K+1], A{K+2], ...
    A[A.length - 1], A[0], A[1], ..., A[K-1].  Afterward, any entries that are less than or equal to their index are worth 1 point.

    For example, if we have [2, 4, 1, 3, 0], and we rotate by K = 2, it becomes [1, 3, 0, 2, 4].
    This is worth 3 points because 1 > 0 [no points], 3 > 1 [no points], 0 <= 2 [one point], 2 <= 3 [one point], 4 <= 4 [one point].

    Over all possible rotations, return the rotation index K that corresponds to the highest score we could receive.
    If there are multiple answers, return the smallest such index K.

    Example 1:
    Input: [2, 3, 1, 4, 0]
    Output: 3
    Explanation:
    Scores for each K are listed below:
    K = 0,  A = [2,3,1,4,0],    score 2
    K = 1,  A = [3,1,4,0,2],    score 3
    K = 2,  A = [1,4,0,2,3],    score 3
    K = 3,  A = [4,0,2,3,1],    score 4
    K = 4,  A = [0,2,3,1,4],    score 3
    So we should choose K = 3, which has the highest score.



    Example 2:
    Input: [1, 3, 0, 2, 4]
    Output: 0
    Explanation:  A will always have 3 points no matter how it shifts.
    So we will choose the smallest K, which is 0.
    Note:

    A will have length at most 20000.
    A[i] will be in the range [0, A.length].




    Solution

    //    (i - A[i] + N) % N

    http://www.cnblogs.com/grandyang/p/9272921.html
    https://leetcode.com/problems/smallest-rotation-with-highest-score/discuss/118725/Easy-and-Concise-5-lines-Solution-C++JavaPython?page=2

 */

// Time Limit Exceeded
fun bestRotation(arr: IntArray): Int {
    val size = arr.size
    val arrK = IntArray(size)
    for ((k, v1) in arr.withIndex()) {
        val arrRotate = IntArray(size) { i ->
            if (i < size - k) arr[i + k] else arr[i - (size - k)]
        }
        var score = 0
        for ((i, v2) in arrRotate.withIndex()) {
            if (v2 <= i) {
                score++
            }
        }
        arrK[k] = score
    }
    return arrK.indexOfFirst { it == arrK.max() }
}

// TODO
fun bestRotation2(A: IntArray): Int {
    val N = A.size
    val bad = IntArray(N)
    for (i in 0 until N) {
        val left = (i - A[i] + 1 + N) % N
        val right = (i + 1) % N
        bad[left]--
        bad[right]++
        if (left > right)
            bad[0]--
    }

    var best = -N
    var ans = 0
    var cur = 0
    for (i in 0 until N) {
        cur += bad[i]
        if (cur > best) {
            best = cur
            ans = i
        }
    }
    return ans
}

// TODO
fun bestRotation3(A: IntArray): Int {
    val N = A.size
    val change = IntArray(N)
    for (i in 0 until N) change[(i - A[i] + 1 + N) % N] -= 1
    var max_i = 0
    for (i in 1 until N) {
        change[i] += change[i - 1] + 1
        max_i = if (change[i] > change[max_i]) i else max_i
    }
    return max_i
}

fun main(args: Array<String>) {

    val arr = intArrayOf(2, 3, 1, 4, 0)
    val arr2 = intArrayOf(1, 3, 0, 2, 4)

    println(bestRotation3(arr))
    println(bestRotation3(arr2))

}
