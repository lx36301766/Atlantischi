package com.leetcode.medium

import com.lx.listnode.ListNode
import com.lx.listnode.printNode

/**

两数相加

    给定两个非空链表来表示两个非负整数。位数按照逆序方式存储，它们的每个节点只存储单个数字。将两数相加返回一个新的链表。

    你可以假设除了数字 0 之外，这两个数字都不会以零开头。

    示例：
        输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
        输出：7 -> 0 -> 8
        原因：342 + 465 = 807


    Solution: https://leetcode-cn.com/problems/add-two-numbers/solution/

 */

private fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
    val root: ListNode? = ListNode(0)
    var cur = root
    var tmp1 = l1
    var tmp2 = l2
    var sum: Int
    var carry: Int
    while (tmp1 != null || tmp2 != null) {
        sum = (cur?.value ?: 0) + ((tmp1?.value ?: 0) + (tmp2?.value ?: 0))
        cur?.value = sum % 10
        tmp1 = tmp1?.next
        tmp2 = tmp2?.next
        carry = sum / 10
        if ((tmp1 != null || tmp2 != null) || carry > 0) {
            cur?.next = ListNode(carry)
        }
        cur = cur?.next
    }
    return root
}

fun main(args: Array<String>) {
    val l1 =
            ListNode(2) {
                ListNode(4) {
                    ListNode(3)
                }
            }
    val l2 =
            ListNode(5) {
                ListNode(6) {
                    ListNode(4)
                }
            }
    val ret = addTwoNumbers(l1, l2)
    println(l1)
    ret?.printNode()

}
