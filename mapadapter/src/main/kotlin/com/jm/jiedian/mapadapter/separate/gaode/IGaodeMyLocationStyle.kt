package com.jm.jiedian.mapadapter.separate.gaode

import com.jm.jiedian.mapadapter.mapapi.IBitmapDescriptor

/**
 * Created on 19/06/2018.

 * @author lx
 */

interface IGaodeMyLocationStyle {

    var myLocationIcon: IBitmapDescriptor

    var radiusFillColor: Int

    var strokeColor: Int

    var strokeWidth: Float

    var myLocationType: Int

    var interval: Long

    var isMyLocationShowing: Boolean

    fun anchor(anchorU: Float, anchorV: Float)

    fun getAnchorU(): Float

    fun getAnchorV(): Float
}
