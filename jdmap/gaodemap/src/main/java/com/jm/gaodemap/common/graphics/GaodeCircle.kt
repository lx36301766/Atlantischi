package com.jm.gaodemap.common.graphics

import com.amap.api.maps.model.Circle
import com.amap.api.maps.model.CircleOptions
import com.jm.jiedian.mapadapter.common.graphics.ICircle

/**
 * Created on 19/06/2018.

 * @author lx
 */

internal class GaodeCircle(private val circle: Circle) : ICircle {

    internal class Options : ICircle.Options {

        val options = CircleOptions()

    }

}