package com.lx.binarytree

/**
 * Created on 28/09/2018.

 * @author lx
 *
 * 1. 给定一个二叉树，请写一个函数输出该二叉树的镜像（最简单的镜像，就是根节点不变，其左右孩子互换位置）
 *
 */


//非递归实现
private fun TreeNode.reverse() {
    val list = mutableListOf(this)
    while (list.isNotEmpty()) {
        val target = list.removeAt(0)
        val tmp = target.left
        target.left = target.right
        target.right = tmp
        target.left?.also { list.add(it) }
        target.right?.also { list.add(it) }
    }
}

//递归实现
private fun TreeNode.reverseRecursive() {
    if (left == null || right == null) {
        return
    }
    val tmpNode = left
    left = right
    right = tmpNode

    left?.reverseRecursive()
    right?.reverseRecursive()
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
    root.reverse()
    println("reverse")
//    root.reverse2()
//    println("reverse2")
    root.printTree()

}

private fun TreeNode.printTree() {
    println(this)
    left?.printTree()
    right?.printTree()
}
