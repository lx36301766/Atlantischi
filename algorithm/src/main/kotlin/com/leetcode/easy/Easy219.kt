package com.leetcode.easy

/**


    给定一个整数数组和一个整数 k，判断数组中是否存在两个不同的索引 i 和 j，使得 nums [i] = nums [j]，并且 i 和 j 的差的绝对值最大为 k。

    Given an array of integers and an integer k, find out whether there are two distinct indices i and j in the array
    such that nums[i] = nums[j] and the absolute difference between i and j is at most k.

    Example 1:

    Input: nums = [1,2,3,1], k = 3
    Output: true
    Example 2:

    Input: nums = [1,0,1,1], k = 1
    Output: true
    Example 3:

    Input: nums = [1,2,3,1,2,3], k = 2
    Output: false

 */

fun containsNearbyDuplicate(nums: IntArray, k: Int): Boolean {
    for (i in 0 until nums.size) {
        for (j in 1 until k + 1) {
            if (i + j < nums.size && nums[i] == nums[i + j]) {
                return true
            }
        }
    }
    return false
}

fun containsNearbyDuplicate2(nums: IntArray, k: Int): Boolean {
    val hashMap = hashMapOf<Int, Int>()
    for ((index, value) in nums.withIndex()) {
        if (hashMap.containsKey(value) && index - hashMap[value]!! <= k) {
            return true
        }
        hashMap[value] = index
    }
    return false
}

fun containsNearbyDuplicate3(nums: IntArray, k: Int): Boolean {
    val set = mutableSetOf<Int>()
    for (i in nums.indices) {
        if (i > k) set.remove(nums[i - k - 1])
        if (!set.add(nums[i])) return true
    }
    return false
}

fun main(args: Array<String>) {

    println(containsNearbyDuplicate3(intArrayOf(1, 2, 3, 1), 3))
    println(containsNearbyDuplicate3(intArrayOf(1, 0, 1, 1), 1))
    println(containsNearbyDuplicate3(intArrayOf(1, 2, 3, 1, 2, 3), 2))

    println(containsNearbyDuplicate3(intArrayOf(99, 99), 2))

}
