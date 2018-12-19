package com.lx

import java.util.*

/**
 * Created on 16/10/2018.
 *
 * @author lx
 *
 *  判断字符串中三种括号是否合法，括号之间可以任意嵌套，但顺序不能倒
 *
 */


fun isBracketMatching(brackets: String): Boolean {
    val map = hashMapOf('(' to ')', '[' to ']', '{' to '}')
    val leftBrackets = map.keys
    val allLegalBrackets = map.keys +  map.values

    val queue = LinkedList<Char>()

    for (bracket in brackets) {
        if (allLegalBrackets.contains(bracket)) {
            if (leftBrackets.contains(bracket)) {
                queue.add(map[bracket] ?: '0')
            } else {
                if (!queue.remove(bracket)) {
                    return false
                }
            }
        } else {
            return false
        }
    }
    return queue.isEmpty()
}

fun main(args: Array<String>) {
    println(isBracketMatching("[]"))
    println(isBracketMatching("([}]"))
    println(isBracketMatching("({)}{[}]"))
    println(isBracketMatching("({)}{[}]]]"))
    println(isBracketMatching("][({)}{[}]"))
}
