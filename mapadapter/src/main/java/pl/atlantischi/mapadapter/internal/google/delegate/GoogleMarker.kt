package pl.atlantischi.mapadapter.internal.google.delegate

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import pl.atlantischi.mapadapter.callback.IMarker
import pl.atlantischi.mapadapter.params.BitmapDescriptorParameters

/**
 * Created on 11/06/2018.

 * @author lx
 */

internal class GoogleMarker(private val marker: Marker): IMarker {

    override fun setIcon(icon: BitmapDescriptorParameters) {
        marker.setIcon(BitmapDescriptorFactory.defaultMarker())
    }

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
        get() = marker.tag
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

}
