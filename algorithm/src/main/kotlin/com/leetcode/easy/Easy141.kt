package com.leetcode.easy

import com.lx.listnode.ListNode

/**

    Given a linked list, determine if it has a cycle in it.

    Follow up:
    Can you solve it without using extra space?

    算法思想就是设置一个快指针fp和一个慢指针sp，两个指针起始同时指向head节点，其中快指针每次走两步，
    慢指针每次走一步，那么如果链表有环的话他们一定能够相遇。可以想象两个人同时从操场上起跑，
    其中甲的速度是乙速度的2倍，那么当乙跑完一圈的时候甲也跑了两圈，他们一定能够相遇。

 */

fun hasCycle(root: ListNode?): Boolean {
    if (root?.next == null) {
        return false
    }
    var fast: ListNode? = root
    var slow: ListNode? = root
    while (fast != null) {
        fast = fast.next?.next
        slow = slow?.next
        if (fast == slow) {
            return true
        }
    }
    return false
}

fun main(args: Array<String>) {

    var start: ListNode? = null
    var last: ListNode? = null
    val root =
            ListNode(1) {
                ListNode(2) {
                    ListNode(3) cycleStart@{
                        start = ListNode(4) {
                            ListNode(5) {
                                ListNode(6) {
                                    ListNode(7) cycleLast@{
                                        last = ListNode(8)
                                        return@cycleLast last
                                    }
                                }
                            }
                        }
                        return@cycleStart start
                    }
                }
            }
    last?.next = start

    println(hasCycle(root))

}
