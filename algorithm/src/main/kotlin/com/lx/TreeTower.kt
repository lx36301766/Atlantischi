package com.lx


fun findMaxTreeLine(data: Array<IntArray>):Int {
    for (i in data.size - 1 downTo 1) {
        for (j in 0 until i) {
            data[i - 1][j] += Math.max(data[i][j], data[i][j + 1])
        }
    }
    return data[0][0]
}

fun main(args: Array<String>) {

    val data = arrayOf(
            intArrayOf(9),
            intArrayOf(12, 15),
            intArrayOf(10, 6, 8),
            intArrayOf(2, 18, 9, 5),
            intArrayOf(19, 7, 10, 4, 16)
    )

    println("max=${findMaxTreeLine(data)}")

}