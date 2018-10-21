package com.leetcode.medium

import java.util.*

/**

    Implement a MyCalendar class to store your events. A new event can be added if adding the event will not cause a double booking.

    Your class will have the method, book(int start, int end). Formally, this represents a booking on the half open interval [start, end),
    the range of real numbers x such that start <= x < end.

    A double booking happens when two events have some non-empty intersection (ie., there is some time that is common to both events.)

    For each call to the method MyCalendar.book, return true if the event can be added to the calendar successfully
    without causing a double booking. Otherwise, return false and do not add the event to the calendar.

    Your class will be called like this: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
    Example 1:
    MyCalendar();
    MyCalendar.book(10, 20); // returns true
    MyCalendar.book(15, 25); // returns false
    MyCalendar.book(20, 30); // returns true
    Explanation:
    The first event can be booked.  The second can't because time 15 is already booked by another event.
    The third event can be booked, as the first event takes every time less than 20, but not including 20.
    Note:

    The number of calls to MyCalendar.book per test case will be at most 1000.
    In calls to MyCalendar.book(start, end), start and end are integers in the range [0, 10^9].




    Solution:
    https://leetcode.com/problems/my-calendar-i/solution/
    https://leetcode.com/problems/my-calendar-i/discuss/109462/java-8-liner-treemap

 */

class MyCalendar {


    val calendar = mutableListOf<Pair<Int, Int>>()

    //Approach #1: Brute Force [Accepted]
    //Time Complexity: O(N)
    fun book(start: Int, end: Int): Boolean {
        calendar.forEach {
            if (start >= it.second || end <= it.first) return@forEach
            return false
        }
        calendar.add(start to end)
        return true
    }



    val calendarTree = TreeMap<Int, Int>()

    //Approach #2: Red Black Tree [Accepted]
    //Time Complexity: O(logN)
    fun book2(start: Int, end: Int): Boolean {
        val prev = calendarTree.floorKey(start)
        val next = calendarTree.ceilingKey(start)
        if ((prev == null || calendarTree[prev] ?: 0 <= start) && (next == null || end <= next)) {
            calendarTree[start] = end
            return true
        }
        return false
    }

    fun book3(start: Int, end: Int): Boolean {
        val low = calendarTree.lowerKey(end)
        if (low == null || calendarTree[low] ?: 0 <= start) {
            calendarTree[start] = end
            return true
        }
        return false
    }

}

fun main(args: Array<String>) {

    val myCalendar = MyCalendar()

    println(myCalendar.book3(10, 20)) // returns true
    println(myCalendar.book3(15, 25)) // returns false
    println(myCalendar.book3(20, 30)) // returns true
    println(myCalendar.book3(5, 11))
    println(myCalendar.book3(30, 35))
    println(myCalendar.book3(30, 35))

}
