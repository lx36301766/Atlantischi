package com.lx.listnode

/**
 * Created on 16/10/2018.

 * @author lx
 */


class ListNode(var value: Int = 0, nextBlock: (ListNode.() -> ListNode?)? = null) {

    var next: ListNode? = nextBlock?.invoke(this)

    override fun toString(): String {
        return "ListNode(value=$value, next=$next)"
    }

}

fun ListNode.printNode() {
    var tmpNode : ListNode? = this
    while (tmpNode != null) {
        print(tmpNode.value)
        if (tmpNode.next != null) {
            print("-")
        }
        tmpNode = tmpNode.next
    }
    println()
}
