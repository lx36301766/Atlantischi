package com.jm.jiedian.mapadapter.internal.gaode.priv

import com.jm.jiedian.mapadapter.separate.gaode.IGaodeAnimation
import com.amap.api.maps.model.animation.Animation

/**
 * Created on 20/06/2018.

 * @author lx
 */

open class GaodeAnimation(val animation: Animation) : IGaodeAnimation {

    override fun setDuration(duration: Long) {
        animation.setDuration(duration)
    }

}
