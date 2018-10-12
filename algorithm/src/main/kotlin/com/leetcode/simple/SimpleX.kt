package com.leetcode.simple

import kotlin.math.absoluteValue

/**
 *
 *  给定一个 32 位有符号整数，将整数中的数字进行反转。
 *
 *  示例 1: 输入: 123 输出: 321
 *
 *  示例 2: 输入: -123 输出: -321
 *
 *  示例 3: 输入: 120 输出: 21
 *
 *  注意:
 *    假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−2.pow(31),  2.pow(31) − 1]。根据这个假设，如果反转后的整数溢出，则返回 0。
 */

fun reverse(x: Int): Int {
    val str = x.absoluteValue.toString()
    val reverseStr = (if (x> 0) "" else "-") + reverseString(str)
    return reverseStr.toInt()
}

fun reverseString(str: String): String {
    return if (str.length == 1) str else str.last() + reverseString(str.substring(0, str.lastIndex))
}

fun main(args: Array<String>) {
    println(reverse(123))
    println(reverse(-321))
    println(reverse(120))
}
