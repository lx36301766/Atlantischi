package com.jm.jiedian.mapadapter.internal.gaode.delegate.graphics

import com.amap.api.maps.model.Circle
import com.amap.api.maps.model.CircleOptions
import com.jm.jiedian.mapadapter.mapapi.graphics.ICircle

/**
 * Created on 19/06/2018.

 * @author lx
 */

internal class GaodeCircle(private val circle: Circle) : ICircle {

    internal class Options : ICircle.Options {

        val options = CircleOptions()

    }

}