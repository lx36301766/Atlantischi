package pl.atlantischi.kotlinandroid.function_reference


/**
 * Created on 29/08/2018.

 * @author lx
 *
 *  函数引用的使用
 *
 */


fun plus(a: Int, b: Int) = a + b

fun minus(a: Int, b: Int) = a - b

fun times(a: Int, b: Int) = a * b

fun div(a: Int, b: Int) = a / b



fun test(block: (Int, Int) -> Int) = block(5, 3)

fun main(args: Array<String>) {
    println(test(::plus))
    println(test(::minus))
    println(test(::times))
    println(test(::div))
}
