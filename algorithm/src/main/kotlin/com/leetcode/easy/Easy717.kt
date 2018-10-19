package com.leetcode.easy

/**

    有两种特殊字符。第一种字符可以用一比特0来表示。第二种字符可以用两比特(10 或 11)来表示。

    现给一个由若干比特组成的字符串。问最后一个字符是否必定为一个一比特字符。给定的字符串总是由0结束。

    示例 1:

    输入:
    bits = [1, 0, 0]
    输出: True
    解释:
    唯一的编码方式是一个两比特字符和一个一比特字符。所以最后一个字符是一比特字符。
    示例 2:

    输入:
    bits = [1, 1, 1, 0]
    输出: False
    解释:
    唯一的编码方式是两比特字符和两比特字符。所以最后一个字符不是一比特字符。
    注意:

    1 <= len(bits) <= 1000.
    bits[i] 总是0 或 1.


 test case

    [1, 0, 0]
    [1, 0, 0, 1, 0]
    [0, 1, 1, 0, 1, 0]
    [1, 1, 0, 1, 1, 0, 0]
    [1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0]
    [0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0]

 */

fun isOneBitCharacter(bits: IntArray): Boolean {
    if (bits.last() != 0) { //最后一位不是 0 视为非法输入
        return false
    }
    bits.forEach {
        if (it != 1 && it != 0) { // 数组中必须全是 1 或 0
            return false
        }
    }
    var index = 0
    var result = false
    // 从头开始遍历，遇到0，index + 1，遇到1，index + 2
    while (index < bits.size) {
        if (bits[index] == 0) {
            index++
            result = true
            continue
        }
        if (bits[index] == 1) {
            if (index == bits.lastIndex - 1) {
                result = false //只有遍历到倒数第二位数仍是1的情况下，才不满足条件
            }
            index += 2
        }
    }
    return result
}

fun main(args: Array<String>) {

    val case0 = intArrayOf(1, 0, 0)
    val case1 = intArrayOf(1, 0, 0, 1, 0)
    val case2 = intArrayOf(0, 1, 1, 0, 1, 0)
    val case3 = intArrayOf(1, 1, 0, 1, 1, 0, 0)
    val case4 = intArrayOf(1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0)
    val case5 = intArrayOf(0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0)

    printCase(case0)
    printCase(case1)
    printCase(case2)
    printCase(case3)
    printCase(case4)
    printCase(case5)

}

fun printCase(case:IntArray) {
    println(String.format("%-5s of %s", isOneBitCharacter(case), case.contentToString()))
}

