package pl.atlantischi.mapadapter.mapapi.graphics

import pl.atlantischi.mapadapter.mapapi.IBitmapDescriptor
import pl.atlantischi.mapadapter.mapapi.ILatLng

/**
 * Created on 11/06/2018.

 * @author lx
 */

interface IMarker {

    var title: String

    var snippet: String

    var tag: Any?

    fun showInfoWindow()

    fun hideInfoWindow()

    fun isInfoWindowShown(): Boolean

    fun setIcon(bitmapDescriptor: IBitmapDescriptor)

    interface Options {

        fun bitmapDescriptor(bitmapDescriptor: IBitmapDescriptor): Options

        fun position(position: ILatLng): Options

        fun zIndex(zIndex: Float): Options

        fun rotation(rotation: Float): Options

        fun alpha(alpha: Float): Options

        fun title(title: String): Options

        fun snippet(snippet: String): Options

        fun visible(visible: Boolean): Options

        fun draggable(draggable: Boolean): Options

        fun flat(flat: Boolean): Options

        fun anchor(anchorU: Float, anchorV: Float): Options

        fun infoWindowAnchor(infoWindowAnchorU: Float, infoWindowAnchorV: Float): Options

    }

}
