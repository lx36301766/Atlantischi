package com.lx.string

/**
 * Created on 16/10/2018.

     @author lx

     判断是否是回文字符串

     回文的定义：“回文数”就是正读倒读都一样的整数。如奇数个数字：98789，这个数字正读是98789 倒读也是98789。偶数个数字3223也是回文数。字母 abcba 也是回文。

 */

//数组方式实现
fun isPalindromeString(str: String): Boolean {
    val length = str.length
    if (length < 2) {
        return true
    }
    val charStr = str.toCharArray()
    val middle = (length - 1) / 2 // 中点位置
    for (i in 0..middle) {
        // 从首尾两端开始遍历访问，判断前后是否相等，只要有一个不相等就返回false
        if (charStr[i] != charStr[length - 1 - i]) {
            return false
        }
    }
    return true
}

fun main(args: Array<String>) {

    println(isPalindromeString("aba"))
    println(isPalindromeString("aabbaa"))
    println(isPalindromeString("aabbcbbaa"))
    println(isPalindromeString("aabb5345c4cb3ba"))

}
