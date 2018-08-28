package pl.atlantischi.kotlinandroid.operators

/**
 * Created on 27/08/2018.

 * @author lx
 *
 * 解构申明和使用
 *
 */

internal class Deconstruction {

    class Password(private val text:String, private val length: Int) {
        operator fun component1() = text
        operator fun component2() = length
    }

    fun foo () {
        val (text, length) = Password("abc", 10)
    }

}
