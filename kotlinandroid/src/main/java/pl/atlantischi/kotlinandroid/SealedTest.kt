package pl.atlantischi.kotlinandroid

/**
 * Created on 2019/4/17.

 * @author lx
 */

sealed class SealedTest {

    object A : SealedTest() {

    }

    object B : SealedTest() {

    }

    inner class D : SealedTest() {

    }

}

object C : SealedTest() {

}

fun main() {
    val s: SealedTest? = null
    when(s) {
        is SealedTest.A -> {}
        is SealedTest.B -> {}
        is C -> {}
    }

}