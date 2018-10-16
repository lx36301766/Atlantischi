package com.leetcode.medium

import com.lx.listnode.ListNode

/**

    Given a linked list, return the node where the cycle begins. If there is no cycle, return null.

    Note: Do not modify the linked list.

    Follow up:
    Can you solve it without using extra space?


    https://blog.csdn.net/willduan1/article/details/50938210

 */

fun detectCycle(head: ListNode?): ListNode? {
    if (head?.next == null) {
        return null
    }
    if (head == head.next?.next) {
        return head
    }
    var fast: ListNode? = head
    var slow: ListNode? = head
    while (fast != null) {
        fast = fast.next?.next
        slow = slow?.next
        if (fast == slow) {
            fast = head
            break
        }
    }
    while (fast != null && slow != null) {
        fast = fast.next
        slow = slow.next
        if (fast == slow) {
            return fast
        }
    }
    return null
}

fun main(args: Array<String>) {

    var start: ListNode? = null
    var last: ListNode? = null
    val root =
            ListNode(1) {
                ListNode(2) {
                    ListNode(3) {
                        ListNode(4) cycleStart@{
                            start =  ListNode(5) {
                                ListNode(6) {
                                    ListNode(7) cycleLast@{
                                        last = ListNode(8)
                                        return@cycleLast last
                                    }
                                }
                            }
                            return@cycleStart start
                        }
                    }
                }
            }
    last?.next = start

    val node1 = ListNode(1)
    val node2 = ListNode(2)
    node1.next = node2
    node2.next = node1

    println(detectCycle(node1))

}
