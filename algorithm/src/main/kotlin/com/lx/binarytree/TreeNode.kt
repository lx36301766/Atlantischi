package com.lx.binarytree

/**
 * Created on 29/09/2018.

 * @author lx
 *
 *
 */

class TreeNode(var value: Int, block: (TreeNode.() -> Unit)? = null) {

    init {
        block?.invoke(this)
    }

    var left: TreeNode? = null
    var right: TreeNode? = null

    override fun toString(): String {
        return "TreeNode(value=$value, left=${left?.value}, right=${right?.value})"
    }

}
