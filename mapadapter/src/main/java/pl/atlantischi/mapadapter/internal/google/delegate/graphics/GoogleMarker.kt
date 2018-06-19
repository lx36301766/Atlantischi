package pl.atlantischi.mapadapter.internal.google.delegate.graphics

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import pl.atlantischi.mapadapter.callback.IBitmapDescriptor
import pl.atlantischi.mapadapter.callback.ILatLng
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



    internal class Options: IMarker.Options {

        val options = MarkerOptions()

        override fun bitmapDescriptor(bitmapDescriptor: IBitmapDescriptor): IMarker.Options {
            if (bitmapDescriptor is GoogleBitmapDescriptor) {
                options.icon(bitmapDescriptor.descriptor)
            }
            return this
        }

        override fun position(position: ILatLng): IMarker.Options {
            options.position(LatLng(position.latitude, position.longitude))
            return this
        }

        override fun zIndex(zIndex: Float): IMarker.Options {
            options.zIndex(zIndex)
            return this
        }

        override fun rotation(rotation: Float): IMarker.Options {
            options.rotation(rotation)
            return this
        }

        override fun alpha(alpha: Float): IMarker.Options {
            options.alpha(alpha)
            return this
        }

        override fun title(title: String): IMarker.Options {
            options.title(title)
            return this
        }

        override fun snippet(snippet: String): IMarker.Options {
            options.snippet(snippet)
            return this
        }

        override fun visible(visible: Boolean): IMarker.Options {
            options.visible(visible)
            return this
        }

        override fun draggable(draggable: Boolean): IMarker.Options {
            options.draggable(draggable)
            return this
        }

        override fun flat(flat: Boolean): IMarker.Options {
            options.flat(flat)
            return this
        }

        override fun anchor(anchorU: Float, anchorV: Float): IMarker.Options {
            options.anchor(anchorU, anchorV)
            return this
        }

        override fun infoWindowAnchor(infoWindowAnchorU: Float, infoWindowAnchorV: Float): IMarker.Options {
            options.infoWindowAnchor(infoWindowAnchorU, infoWindowAnchorV)
            return this
        }

    }


}
