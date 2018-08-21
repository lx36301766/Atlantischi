package pl.atlantischi.test

/**
 * Created on 13/07/2018.

 * @author lx
 */

//给定 nums = [2, 7, 11, 15], target = 9
//因为 nums[0] + nums[1] = 2 + 7 = 9
//所以返回 [0, 1]

fun main(args: Array<String>) {
    val result = twoSum(intArrayOf(2, 7, 11, 15, 18, 23, 33, 44, 63), 86)
    print(result.contentToString())
}

fun twoSum(nums: IntArray, target: Int): IntArray {
    val ret = IntArray(2)
    val size = nums.size
    for (i in nums.indices) {
        println("i=$i")
        for (j in i + 1 until size) {
            println("j=$j")
            if (nums[i] + nums[j] == target) {
                ret[0] = i
                ret[1] = j
                return ret
            }
        }
    }
    return ret
}