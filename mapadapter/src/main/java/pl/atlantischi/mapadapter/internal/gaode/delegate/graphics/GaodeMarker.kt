package pl.atlantischi.mapadapter.internal.gaode.delegate.graphics

import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.animation.Animation
import pl.atlantischi.mapadapter.callback.IBitmapDescriptor
import pl.atlantischi.mapadapter.callback.graphics.IMarker
import pl.atlantischi.mapadapter.internal.gaode.delegate.GaodeBitmapDescriptor

/**
 * Created on 11/06/2018.

 * @author lx
 */

internal class GaodeMarker(private val marker: Marker) : IMarker {

    override var title = marker.title
        set(value) {
            marker.title = value
        }

    override var snippet = marker.snippet
        set(value) {
            marker.snippet = value
        }

    override var tag = marker.`object`
        set(value) {
            marker.`object` = value
        }

    override fun showInfoWindow() {
        marker.showInfoWindow()
    }

    override fun hideInfoWindow() {
        marker.hideInfoWindow()
    }

    override fun isInfoWindowShown(): Boolean {
        return marker.isInfoWindowShown
    }

    override fun setIcon(bitmapDescriptor: IBitmapDescriptor) {
        if (bitmapDescriptor is GaodeBitmapDescriptor) {
            marker.setIcon(bitmapDescriptor.descriptor)
        }
    }

    fun setAnimation(animation: Animation) {
        marker.setAnimation(animation)
    }

    fun startAnimation(): Boolean {
        return marker.startAnimation()
    }

}
