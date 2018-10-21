package com.leetcode.easy

/**

    两数之和

    给定一个整数数组和一个目标值，找出数组中和为目标值的两个数。
    你可以假设每个输入只对应一种答案，且同样的元素不能被重复利用。

    示例:

    给定 nums = [2, 7, 11, 15], target = 9
    因为 nums[0] + nums[1] = 2 + 7 = 9
    所以返回 [0, 1]

 */

//方法一：暴力法
//时间复杂度：O(n^2)
//空间复杂度：O(1)
private fun twoSum(nums: IntArray, target: Int): IntArray {
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
    throw IllegalArgumentException("No two sum solution")
}

//方法二：两遍哈希表
//时间复杂度：O(n)
//空间复杂度：O(n)
private fun twoSum2(nums: IntArray, target: Int): IntArray {
    val map = HashMap<Int, Int>()
    for (i in nums.indices) {
        map[nums[i]] = i
    }
    for (i in nums.indices) {
        val complement = target - nums[i]
        if (map.containsKey(complement) && map[complement] != i) {
            return intArrayOf(i, map[complement] ?: -1)
        }
    }
    throw IllegalArgumentException("No two sum solution")
}

fun main(args: Array<String>) {
    val result = twoSum2(intArrayOf(2, 7, 11, 15, 18, 23, 33, 44, 63), 56)
    print(result.contentToString())
}
