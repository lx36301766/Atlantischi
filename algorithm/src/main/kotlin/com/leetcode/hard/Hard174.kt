package com.leetcode.hard

/**
 * Created on 2018/10/24.


    The demons had captured the princess (P) and imprisoned her in the bottom-right corner of a dungeon.
    The dungeon consists of M x N rooms laid out in a 2D grid. Our valiant knight (K) was initially positioned in the top-left room
    and must fight his way through the dungeon to rescue the princess.

    The knight has an initial health point represented by a positive integer. If at any point his health point drops to 0 or below, he dies immediately.

    Some of the rooms are guarded by demons, so the knight loses health (negative integers) upon entering these rooms;
    other rooms are either empty (0's) or contain magic orbs that increase the knight's health (positive integers).

    In order to reach the princess as quickly as possible, the knight decides to move only rightward or downward in each step.



    Write a function to determine the knight's minimum initial health so that he is able to rescue the princess.

    For example, given the dungeon below, the initial health of the knight must be at least 7 if he follows the optimal path RIGHT-> RIGHT -> DOWN -> DOWN.

    -2 (K)	-3	3
    -5	-10	1
    10	30	-5 (P)


    Note:

    The knight's health has no upper bound.
    Any room can contain threats or power-ups, even the first room the knight enters and the bottom-right room where the princess is imprisoned.



    解题思路：逆向动态规划

    https://www.cnblogs.com/duanqiong/p/4417590.html

 */


fun calculateMinimumHP(dungeon: Array<IntArray>): Int {
    if (dungeon.isEmpty() || dungeon[0].isEmpty()) {
        return 0
    }
    val rowSize = dungeon.size
    val columnSize = dungeon[0].size

    for (i in rowSize - 1 downTo 0) {
        if (columnSize != dungeon[i].size) {
            return 0
        }
        for (j in columnSize - 1 downTo 0) {
            dungeon[i][j] =
                    if (i == dungeon.size - 1 && j == dungeon[i].size - 1) {
                        Math.max(1 - dungeon[i][j], 1)
                    } else if (i == dungeon.size - 1) {
                        Math.max(dungeon[i][j + 1] - dungeon[i][j], 1)
                    } else if (j == dungeon[i].size - 1) {
                        Math.max(dungeon[i + 1][j] - dungeon[i][j], 1)
                    } else {
                        Math.max(Math.min(dungeon[i + 1][j], dungeon[i][j + 1]) - dungeon[i][j], 1)
                    }
        }
    }
    return dungeon[0][0]
}

fun main(args: Array<String>) {

    println(calculateMinimumHP(arrayOf(
            intArrayOf(-2, -3, 3),
            intArrayOf(-5, -10, 1),
            intArrayOf(10, 30, -5)
    )))

    println(calculateMinimumHP(arrayOf(
            intArrayOf( 0,  5, -3,  2),
            intArrayOf(-5, -8,  1, -8),
            intArrayOf(10, -1, -5,  3),
            intArrayOf(-9,  2, -5,  0)
    )))

    println(calculateMinimumHP(arrayOf(
            intArrayOf(1, -2, 3),
            intArrayOf(2, -2, -2)
    )))

}