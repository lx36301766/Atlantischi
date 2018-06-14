package pl.atlantischi.mapadapter.internal.gaode.delegate

import com.amap.api.maps.model.Marker
import pl.atlantischi.mapadapter.callback.IBitmapDescriptor
import pl.atlantischi.mapadapter.callback.IMarker

/**
 * Created on 11/06/2018.

 * @author lx
 */

internal class GaodeMarker(private val marker: Marker) : IMarker {

    override var title: String
        get() = marker.title
        set(value) {
            marker.title = value
        }

    override var snippet: String
        get() = marker.snippet
        set(value) {
            marker.snippet = value
        }

    override var tag: Any?
        get() = marker.`object`
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

}
