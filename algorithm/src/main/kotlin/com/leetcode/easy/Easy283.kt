package com.leetcode.easy

/**

    Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.

    Example:

    Input: [0,1,0,3,12]
    Output: [1,3,12,0,0]
    Note:

    You must do this in-place without making a copy of the array.
    Minimize the total number of operations.

 */


fun moveZeroes(nums: IntArray) {
    var zeroCount = 0
    for (i in nums.indices) {
        if (nums[i] == 0) {
            zeroCount++
        } else {
            if (zeroCount != 0) {
                nums[i - zeroCount] = nums[i]
                nums[i] = 0
            }
        }
    }
}

fun main(args: Array<String>) {
    val arr = intArrayOf(1, 9, 2, 0, 6, 5, 7, 0, 0, 4)
    moveZeroes(arr)
    println(arr.contentToString())
}

