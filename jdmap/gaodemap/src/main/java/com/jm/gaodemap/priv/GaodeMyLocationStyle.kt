package com.jm.gaodemap.priv

import com.amap.api.maps.model.MyLocationStyle
import com.jm.jiedian.mapadapter.priv.gaode.IGaodeMyLocationStyle
import com.jm.gaodemap.common.GaodeBitmapDescriptor
import com.jm.jiedian.mapadapter.common.IBitmapDescriptor

/**
 * Created on 19/06/2018.

 * @author lx
 */

internal class GaodeMyLocationStyle : IGaodeMyLocationStyle {

    val myLocationStyle = MyLocationStyle()

    override var myLocationIcon: IBitmapDescriptor?
        get() = GaodeBitmapDescriptor(myLocationStyle.myLocationIcon)
        set(value) {
            val gbd = value as? GaodeBitmapDescriptor
            myLocationStyle.myLocationIcon(gbd?.descriptor)
        }

    override var radiusFillColor: Int
        get() = myLocationStyle.radiusFillColor
        set(value) {
            myLocationStyle.radiusFillColor(value)
        }

    override var strokeColor: Int
        get() = myLocationStyle.strokeColor
        set(value) {
            myLocationStyle.strokeColor(value)
        }

    override var strokeWidth: Float
        get() = myLocationStyle.strokeWidth
        set(value) {
            myLocationStyle.strokeWidth(value)
        }

    override var myLocationType: Int
        get() = myLocationStyle.myLocationType
        set(value) {
            myLocationStyle.myLocationType(value)
        }

    override var interval: Long
        get() = myLocationStyle.interval
        set(value) {
            myLocationStyle.interval(value)
        }

    override var isMyLocationShowing: Boolean
        get() = myLocationStyle.isMyLocationShowing
        set(value) {
            myLocationStyle.showMyLocation(value)
        }

    override fun anchor(anchorU: Float, anchorV: Float) {
        myLocationStyle.anchor(anchorU, anchorV)
    }

    override fun getAnchorU(): Float {
        return myLocationStyle.anchorU
    }

    override fun getAnchorV(): Float {
        return myLocationStyle.anchorV
    }
}
