package com.lx.binarytree

/**
 * Created on 29/09/2018.

 * @author lx
 *
 *
 *  给定一个二叉树的根节点，请按行从上到下Z字形（即如果第n行是从左至右打印的，则从右至左打印第n+1行）打印出该二叉树所有节点的值
 */

private fun TreeNode.printZBinaryTree() {

    var list = mutableListOf(this)
    left?.also { list.add(it) }
    right?.also { list.add(it) }

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

    root.printZBinaryTree()
}
