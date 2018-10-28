package com.lx.binarytree

import java.util.*

/**
 * Created on 29/09/2018.

 * @author lx
 *
 *
 */

// 这里block参数的目的是为了通过DSL方式更直观的构造测试数据
class  TreeNode(var value: Int, block: (TreeNode.() -> Unit)? = null) {

    init {
        block?.invoke(this)
    }

    var left: TreeNode? = null
    var right: TreeNode? = null

    override fun toString(): String {
        return "TreeNode(value=$value, left=${left?.value}, right=${right?.value})"
    }

}

//递归打印
fun TreeNode.printThis() {
    println(this)
    left?.printThis()
    right?.printThis()
}

//换行遍历打印
fun TreeNode.printTree() {
    val queue = LinkedList<TreeNode>()
    var current: Int//当前层 还未打印的结点个数
    var next: Int//下一层结点个数
    queue.add(this)
    current = 1
    next = 0
    while (!queue.isEmpty()) {
        val currentNode = queue.poll()
        print("${currentNode.value}   ")
        current--
        currentNode.left?.also {
            queue.offer(it)
            next++
        }
        currentNode.right?.also {
            queue.offer(it)
            next++
        }
        if (current == 0) {
            println()
            current = next
            next = 0
        }
    }
    println()
}

// 更漂亮的格式换行遍历打印
fun TreeNode.printTreePretty() {
    val queue = LinkedList<TreeNode>()
    var current: Int//当前层 还未打印的结点个数
    var next: Int//下一层结点个数
    queue.add(this)
    current = 1
    next = 0

    val printList = LinkedList<String>()

    while (!queue.isEmpty()) {
        val currentNode = queue.poll()
//        print("${currentNode.value}   ")
        printList.addFirst("${currentNode.value}   ")
        current--
        currentNode.left?.also {
            queue.offer(it)
            next++
        }
        currentNode.right?.also {
            queue.offer(it)
            next++
        }
        if (current == 0) {
//            println()
            printList.addFirst("\n")
            current = next
            next = 0
        }
    }

    printList.forEach {
        print(it)
    }

}
