package com.leetcode.easy

/**

    Given an integer array, you need to find one continuous subarray that if you only sort this subarray in ascending order,
    then the whole array will be sorted in ascending order, too.

    You need to find the shortest such subarray and output its length.

    Example 1:
    Input: [2, 6, 4, 8, 10, 9, 15]
    Output: 5
    Explanation: You need to sort [6, 4, 8, 10, 9] in ascending order to make the whole array sorted in ascending order.
    Note:
    Then length of the input array is in range [1, 10,000].
    The input array may contain duplicates, so ascending order here means <=.

 */

//Time complexity : O(n)O(n). Four O(n)O(n) loops are used.
//Space complexity : O(1)O(1). Constant space is used.
fun findUnsortedSubarray(nums: IntArray): Int {
    var min = Integer.MAX_VALUE
    var max = Integer.MIN_VALUE
    var flag = false
    for (i in 1 until nums.size) {
        if (nums[i] < nums[i - 1])
            flag = true
        if (flag)
            min = Math.min(min, nums[i])
    }
    flag = false
    for (i in nums.size - 2 downTo 0) {
        if (nums[i] > nums[i + 1])
            flag = true
        if (flag)
            max = Math.max(max, nums[i])
    }
    var l = 0
    var r = nums.size - 1
    while (l < nums.size) {
        if (min < nums[l])
            break
        l++
    }
    while (r >= 0) {
        if (max > nums[r])
            break
        r--
    }
    return if (r - l < 0) 0 else r - l + 1
}


// sort
//Time complexity : O(nlogn)O(nlogn). Sorting takes nlognnlogn time.
//Space complexity : O(n)O(n). We are making copy of original array.

fun findUnsortedSubarray2(nums: IntArray): Int {
    var l = nums.size
    var r = 0
    for (i in 0 until nums.size - 1) {
        for (j in i + 1 until nums.size) {
            if (nums[j] < nums[i]) {
                r = Math.max(r, j)
                l = Math.min(l, i)
            }
        }
    }
    return if (r - l < 0) 0 else r - l + 1
}

fun main(args: Array<String>) {

    println(findUnsortedSubarray2(intArrayOf(1)))
    println(findUnsortedSubarray2(intArrayOf(2, 1)))
    println(findUnsortedSubarray2(intArrayOf(1, 2, 3, 4)))
    println(findUnsortedSubarray2(intArrayOf(1, 3, 5, 4, 2)))
    println(findUnsortedSubarray2(intArrayOf(12, 21, 9, 11, 15)))
    println(findUnsortedSubarray2(intArrayOf(8, 6, 5, 7, 9)))
    println(findUnsortedSubarray2(intArrayOf(3, 2, 1)))
    println(findUnsortedSubarray2(intArrayOf(2, 6, 4, 8, 10, 9, 15)))
    println(findUnsortedSubarray2(intArrayOf(2, 3, 3, 5, 5, 6, 4, 8, 10, 9, 9, 10, 12, 15)))

}
