package pl.atlantischi.mapadapter.internal.gaode.priv

import com.amap.api.maps.model.MyLocationStyle
import pl.atlantischi.mapadapter.extapi.gaode.IMyLocationStyle
import pl.atlantischi.mapadapter.internal.gaode.delegate.GaodeBitmapDescriptor
import pl.atlantischi.mapadapter.mapapi.IBitmapDescriptor

/**
 * Created on 19/06/2018.

 * @author lx
 */

internal class GaodeMyLocationStyle : IMyLocationStyle {

    val myLocationStyle = MyLocationStyle()

    override var myLocationIcon: IBitmapDescriptor
        get() = GaodeBitmapDescriptor(myLocationStyle.myLocationIcon)
        set(value) {
            val gbd = value as GaodeBitmapDescriptor
            myLocationStyle.myLocationIcon(gbd.descriptor)
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
