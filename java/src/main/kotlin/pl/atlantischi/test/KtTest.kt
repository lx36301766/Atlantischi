package pl.atlantischi.test

import java.lang.reflect.Method

/**
 * Created on 05/07/2017.

 * @author lx
 */

fun main(args: Array<String>) {
    println("max of 0 and 42 is")

    val map = mapOf("a" to 1, "b" to 2, "c" to 3)
    for ((key, value) in map) {
        println("key=$key, value=$value")
    }

    var person = Person("", "")

    val (name, age) = Person("", "")

    val a = run {
        println("run")
        return@run 3
    }
    println(a)


    var str = ""
    str?.run {
        println("this=$this")
        println("length=$length")
    }
    str?.let {
        println("this=$it")
        println("length=${it.length}")
    }

    val method: Method = String::class.java.getMethod("")
    method.getAnnotation(Target::class.java)
}

data class Person(var name: String, var age: String)

