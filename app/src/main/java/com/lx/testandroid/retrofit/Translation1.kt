package com.lx.testandroid.retrofit

/**
 * Created on 17/07/2018.
 *
 * @author lx
 */

class Translation1 {

    var type: String? = null
    var errorCode: Int = 0
    var elapsedTime: Int = 0
    var translateResult: List<TranslateResultBean>? = null

    class TranslateResultBean {

        var src: String? = null
        var tgt: String? = null
    }

}
