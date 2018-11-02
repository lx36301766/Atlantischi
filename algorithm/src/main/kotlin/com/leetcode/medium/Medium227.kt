package com.leetcode.medium

import java.util.Stack



/**

    Implement a basic calculator to evaluate a simple expression string.

    The expression string contains only non-negative integers, +, -, *, / operators and empty spaces .
    The integer division should truncate toward zero.

    Example 1:

    Input: "3+2*2"
    Output: 7
    Example 2:

    Input: " 3/2 "
    Output: 1
    Example 3:

    Input: " 3+5 / 2 "
    Output: 5
    Note:

    You may assume that the given expression is always valid.
    Do not use the eval built-in library function.

 */


fun calculate(str: String): Int {
    if (str.isBlank()) {
        return 0
    }
    var str = str.filterNot { it.isWhitespace() }

    val isDigit: (Char) -> Boolean = { Character.isDigit(it) }

    val arr = str.toCharArray()
    val list = mutableListOf<String>()

//    val tmpNum = StringBuilder()
//    for ((index, value) in arr.withIndex()) {
//        if (index == arr.lastIndex) {
//            list.add(tmpNum.append(value).toString())
//        }
//        if (isDigit(value)) {
//            tmpNum.append(value)
//            continue
//        }
//        list.add(tmpNum.toString())
//        list.add(value.toString())
//        tmpNum.setLength(0)
//    }

    var num = 0
    for ((index, value) in arr.withIndex()) {
        if (isDigit(value)) {
            num = num * 10 + value.toInt() - '0'.toInt()
            if (index == arr.lastIndex) {
                list.add(num.toString())
            }
            continue
        }
        list.add(num.toString())
        list.add(value.toString())
        num = 0
    }

    var prevPlusMinus: String? = null
    var count = 0
    var first: Int
    var second: Int

    for ((index, value) in list.withIndex()) {
        if (index % 2 == 1) {
            first = list[index - 1].toInt()
            second = list[index + 1].toInt()
            when (value) {
                "*" -> {
                    count++
                    list[index + 1] = (first * second).toString()
                }
                "/" -> {
                    count++
                    list[index + 1] = (first / second).toString()
                }
                "+", "-" -> {
                    when (prevPlusMinus) {
                        "+" -> {
                            list[index - 1] = (list[index - count * 2 - 3].toInt() + first).toString()
                        }
                        "-" -> {
                            list[index - 1] = (list[index - count * 2 - 3].toInt() - first).toString()
                        }
                    }
                    prevPlusMinus = value
                    count = 0
                }
            }
        }
        if (index == list.lastIndex && prevPlusMinus != null) {
            first = list[index - count * 2 - 2].toInt()
            second = list[index].toInt()
            when (prevPlusMinus) {
                "+" -> {
                    list[index] = (first + second).toString()
                }
                "-" -> {
                    list[index] = (first - second).toString()
                }
            }
        }
    }

    return list.last().toInt()
}

fun calculate2(s: String?): Int {
    if (s == null || s.isEmpty()) return 0
    val len = s.length
    val stack = Stack<Int>()
    var num = 0
    var sign = '+'
    for (i in 0 until len) {
        if (Character.isDigit(s[i])) {
            num = num * 10 + s[i].toInt() - '0'.toInt()
        }
        if (!Character.isDigit(s[i]) && ' ' != s[i] || i == len - 1) {
            if (sign == '-') {
                stack.push(-num)
            }
            if (sign == '+') {
                stack.push(num)
            }
            if (sign == '*') {
                stack.push(stack.pop() * num)
            }
            if (sign == '/') {
                stack.push(stack.pop() / num)
            }
            sign = s[i]
            num = 0
        }
    }

    var re = 0
    for (i in stack) {
        re += i
    }
    return re
}

fun main(args: Array<String>) {

    println(calculate2("1*2-3/4+5*6-7*8+9/10"))
    println(calculate2("1 + 4"))
    println(calculate2("2 * 3"))
    println(calculate2("3 + 24 * 2"))
    println(calculate2(" 3 / 2 + 3 - 3 - 6 * 3 * 2"))
    println(calculate2("31 - 14 * 25 / 244 + 4-1133 + 35* 124"))

}
