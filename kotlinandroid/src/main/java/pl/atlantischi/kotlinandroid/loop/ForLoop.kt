package pl.atlantischi.kotlinandroid.loop

/**
 * Created on 27/08/2018.

 * @author lx
 */

internal class ForLoop {

    private val stringList = intArrayOf(1, 2, 2, 33, 3)


    fun foo() {
        for (value in stringList) {

        }
        for (index in stringList.indices) {

        }
        for ((index, value) in stringList.withIndex()) {

        }

        for (index in 0 until stringList.size step 2) {

        }
        for (index in stringList.size downTo 0 step 2) {

        }

        for (i in 5..stringList.size) {

        }

    }

}
