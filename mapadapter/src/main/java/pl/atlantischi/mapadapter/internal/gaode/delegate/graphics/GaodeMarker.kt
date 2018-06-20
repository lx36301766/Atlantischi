package pl.atlantischi.mapadapter.internal.gaode.delegate.graphics

import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import pl.atlantischi.mapadapter.internal.gaode.delegate.GaodeAnimation
import pl.atlantischi.mapadapter.mapapi.IBitmapDescriptor
import pl.atlantischi.mapadapter.mapapi.ILatLng
import pl.atlantischi.mapadapter.mapapi.graphics.IMarker
import pl.atlantischi.mapadapter.internal.gaode.delegate.GaodeBitmapDescriptor
import pl.atlantischi.mapadapter.separate.gaode.IGaodeAnimation
import pl.atlantischi.mapadapter.separate.gaode.IGaodeMarker

/**
 * Created on 11/06/2018.

 * @author lx
 */

internal class GaodeMarker(private val marker: Marker) : IGaodeMarker {

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



    /************************* private api *****************************/

    override fun setAnimation(animation: IGaodeAnimation) {
        val ga = animation as GaodeAnimation
        marker.setAnimation(ga.animation)
    }

    override fun startAnimation(): Boolean {
        return marker.startAnimation()
    }


    internal class Options: IMarker.Options {

        val options = MarkerOptions()

        override fun bitmapDescriptor(bitmapDescriptor: IBitmapDescriptor): IMarker.Options {
            if (bitmapDescriptor is GaodeBitmapDescriptor) {
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
            options.rotateAngle(rotation)
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
            options.isFlat = flat
            return this
        }

        override fun anchor(anchorU: Float, anchorV: Float): IMarker.Options {
            options.anchor(anchorU, anchorV)
            return this
        }

        override fun infoWindowAnchor(infoWindowAnchorU: Float, infoWindowAnchorV: Float): IMarker.Options {
            options.setInfoWindowOffset(infoWindowAnchorU.toInt(), infoWindowAnchorV.toInt())
            return this
        }

    }

}
