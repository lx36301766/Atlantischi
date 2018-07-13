package com.leetcode.simple

/**
 *
 *  给定一个整数数组和一个目标值，找出数组中和为目标值的两个数。
 *  你可以假设每个输入只对应一种答案，且同样的元素不能被重复利用。
 *
 *  示例:
 *
 *  给定 nums = [2, 7, 11, 15], target = 9
 *  因为 nums[0] + nums[1] = 2 + 7 = 9
 *  所以返回 [0, 1]
 *
 */

fun twoSum(nums: IntArray, target: Int): IntArray {
    val ret = IntArray(2)
    val size = nums.size
    for (i in nums.indices) {
        for (j in i + 1 until size) {
            if (nums[i] + nums[j] == target) {
                ret[0] = i
                ret[1] = j
                return ret
            }
        }
    }
    return ret
}

fun main(args: Array<String>) {
    val result = twoSum(intArrayOf(2, 7, 11, 15, 18, 23, 33, 44, 63), 421)
    print(result.contentToString())
}
