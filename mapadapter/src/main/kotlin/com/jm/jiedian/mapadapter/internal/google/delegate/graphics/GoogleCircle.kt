package com.jm.jiedian.mapadapter.internal.google.delegate.graphics

import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.jm.jiedian.mapadapter.mapapi.graphics.ICircle

/**
 * Created on 19/06/2018.

 * @author lx
 */

internal class GoogleCircle(private val circle: Circle) : ICircle {

    internal class Options : ICircle.Options {

        val options = CircleOptions()

    }

}