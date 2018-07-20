package com.lx.testandroid.retrofit

/**
 * Created on 17/07/2018.

 * @author lx
 */

data class Translation(var status: Int, var content: Content) {

    data class Content(var from: String, var to: String, var vendor: String, var out: String, var errNo: Int)

    fun show() {
        System.out.println(status)
        System.out.println(content.from)
        System.out.println(content.to)
        System.out.println(content.vendor)
        System.out.println(content.out)
        System.out.println(content.errNo)
    }

}
