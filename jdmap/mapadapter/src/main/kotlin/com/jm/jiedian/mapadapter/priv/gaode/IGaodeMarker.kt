package com.jm.jiedian.mapadapter.priv.gaode

import com.jm.jiedian.mapadapter.common.graphics.IMarker

/**
 * Created on 20/06/2018.

 * @author lx
 */

interface IGaodeMarker : IMarker {

    fun setAnimation(animation: IGaodeAnimation)

    fun startAnimation(): Boolean


    interface Options : IMarker.Options {
        fun infoWindowEnable(enable: Boolean): IMarker.Options
    }

}
