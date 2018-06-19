package pl.atlantischi.mapadapter.internal.google.delegate.graphics

import com.google.android.gms.maps.model.Marker
import pl.atlantischi.mapadapter.callback.IBitmapDescriptor
import pl.atlantischi.mapadapter.callback.graphics.IMarker
import pl.atlantischi.mapadapter.internal.google.delegate.GoogleBitmapDescriptor

/**
 * Created on 11/06/2018.

 * @author lx
 */

internal class GoogleMarker(private val marker: Marker): IMarker {

    override var title = marker.title
        set(value) {
            marker.title = value
        }

    override var snippet = marker.snippet
        set(value) {
            marker.snippet = value
        }

    override var tag = marker.tag
        set(value) {
            marker.tag = value
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
        if (bitmapDescriptor is GoogleBitmapDescriptor) {
            marker.setIcon(bitmapDescriptor.descriptor)
        }
    }

}
