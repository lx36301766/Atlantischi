package com.lx.binarytree

import java.util.*
import kotlin.math.log2


/**
 * Created on 29/09/2018.

 * @author lx
 *
 *
 *  给定一个二叉树的根节点，请按行从上到下Z字形（即如果第n行是从左至右打印的，则从右至左打印第n+1行）打印出该二叉树所有节点的值
 */

//方法1
private fun TreeNode.printZBinaryTree() {
    val list = LinkedList<TreeNode>().apply { add(this@printZBinaryTree) }
    var leftToRight = true //起始遍历方向
    var row = 1 // 当前所在的二叉树的行数，即深度值
    var index = 0 // 当前子节点的索引值
    while (list.isNotEmpty()) {
        index++
        val tmpRow = log2((index).toDouble()).toInt() //行数和索引的关系为：row=log2（index）
        if (tmpRow != row) { //行数不相等时，说明开始遍历下一行，此时反转方向
            row = tmpRow
            leftToRight = !leftToRight
        }
        var node : TreeNode
        //取出当前节点的方向和添加子节点的方向一定要相反，不然遍历打印的顺序就乱了
        if (leftToRight) {
            node = list.removeFirst() // 正向遍历，从队列的前面取出当前节点
            node.left?.also { list.addLast(it) } // 先添加左子节点，并将其添加到队列末尾
            node.right?.also { list.addLast(it) }
        } else {
            node = list.removeLast() //反向遍历，从队列的后面取出当前节点
            node.right?.also { list.addFirst(it) } // 先添加右子节点，并将其添加到队列前端
            node.left?.also { list.addFirst(it) }
        }
        println("value= ${node.value}  row= $row  leftToRight=$leftToRight")
    }
}

//方法2
private fun TreeNode.printZBinaryTree2() {
    val queue = LinkedList<TreeNode>()
    queue.addLast(null) //添加第一个分层符，表示第一层的开始
    queue.addLast(this)
    var leftToRight = false //控制遍历方向的标志位
    while (queue.size != 1) {
        val node = queue.removeFirst() //每次取出队头元素
        if (node == null) { //到达分层符
            var iter: Iterator<TreeNode>?
            if (leftToRight) {
                iter = queue.iterator() //从前往后遍历
            } else {
                iter = queue.descendingIterator() //从后往前遍历
            }
            leftToRight = !leftToRight //标志位反向
            while (iter!!.hasNext()) {
                print("${iter.next().value} ") //打印当前节点的值
            }
            println() //打印换行符
            queue.addLast(null) //该层已打印完毕，添加另一个分层符
            continue //这里一定要continue，因为由分层符触发的遍历只负责打印，不负责继续添加节点
        }
        node.left?.also { queue.addLast(it) } //遍历到普通节点，则将其左右孩子分别添加到队尾
        node.right?.also { queue.addLast(it) }
    }
}

fun main(args: Array<String>) {
    var root =
            TreeNode(1) {
                left = TreeNode(2) {
                    left = TreeNode(4) {
                        left = TreeNode(8)
                        right = TreeNode(9)
                    }
                    right = TreeNode(5) {
                        left = TreeNode(10)
                        right = TreeNode(11)
                    }
                }
                right = TreeNode(3) {
                    left = TreeNode(6) {
                        left = TreeNode(12)
                        right = TreeNode(13)
                    }
                    right = TreeNode(7) {
                        left = TreeNode(14)
                        right = TreeNode(15)
                    }
                }
            }

    root.printTree()
    root.printZBinaryTree()
    root.printTree()
}
