package pl.atlantischi.mapadapter.internal.gaode.delegate.graphics

import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import pl.atlantischi.mapadapter.callback.IBitmapDescriptor
import pl.atlantischi.mapadapter.callback.ILatLng
import pl.atlantischi.mapadapter.callback.IMarkerOptions
import pl.atlantischi.mapadapter.internal.gaode.delegate.GaodeBitmapDescriptor

/**
 * Created on 11/06/2018.

 * @author lx
 */

internal class GaodeMarkerOptions: IMarkerOptions {

    val options = MarkerOptions()

    override fun bitmapDescriptor(bitmapDescriptor: IBitmapDescriptor): IMarkerOptions {
        if (bitmapDescriptor is GaodeBitmapDescriptor) {
            options.icon(bitmapDescriptor.descriptor)
        }
        return this
    }

    override fun position(position: ILatLng): IMarkerOptions {
        options.position(LatLng(position.latitude, position.longitude))
        return this
    }

    override fun zIndex(zIndex: Float): IMarkerOptions {
        options.zIndex(zIndex)
        return this
    }

    override fun rotation(rotation: Float): IMarkerOptions {
        options.rotateAngle(rotation)
        return this
    }

    override fun alpha(alpha: Float): IMarkerOptions {
        options.alpha(alpha)
        return this
    }

    override fun title(title: String): IMarkerOptions {
        options.title(title)
        return this
    }

    override fun snippet(snippet: String): IMarkerOptions {
        options.snippet(snippet)
        return this
    }

    override fun visible(visible: Boolean): IMarkerOptions {
        options.visible(visible)
        return this
    }

    override fun draggable(draggable: Boolean): IMarkerOptions {
        options.draggable(draggable)
        return this
    }

    override fun flat(flat: Boolean): IMarkerOptions {
        options.isFlat = flat
        return this
    }

    override fun anchor(anchorU: Float, anchorV: Float): IMarkerOptions {
        options.anchor(anchorU, anchorV)
        return this
    }

    override fun infoWindowAnchor(infoWindowAnchorU: Float, infoWindowAnchorV: Float): IMarkerOptions {
        options.setInfoWindowOffset(infoWindowAnchorU.toInt(), infoWindowAnchorV.toInt())
        return this
    }

}
