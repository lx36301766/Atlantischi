package com.lx.binarytree


/**
 * Created on 29/09/2018.

 * @author lx
 *
 *
 *  给定一个二叉树的根节点，请写一个函数判断该二叉树是否是平衡二叉树（平衡二叉树的定义是一颗二叉树，其任意节点的左右子树的深度相差不超过1）
 */


//TODO
fun TreeNode.isBalancedTree():Boolean {

    return false
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

    root.printTreePretty()

//    println("isBalancedTree = ${root.isBalancedTree()}")



}
