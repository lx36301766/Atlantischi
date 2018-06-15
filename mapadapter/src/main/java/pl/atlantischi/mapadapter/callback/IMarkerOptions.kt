package pl.atlantischi.mapadapter.callback

/**
 * Created on 15/06/2018.

 * @author lx
 */

interface IMarkerOptions {

    fun bitmapDescriptor(bitmapDescriptor: IBitmapDescriptor): IMarkerOptions

    fun position(position: ILatLng): IMarkerOptions

    fun zIndex(zIndex: Float): IMarkerOptions

    fun rotation(rotation: Float): IMarkerOptions

    fun alpha(alpha: Float): IMarkerOptions

    fun title(title: String): IMarkerOptions

    fun snippet(snippet: String): IMarkerOptions

    fun visible(visible: Boolean): IMarkerOptions

    fun draggable(draggable: Boolean): IMarkerOptions

    fun flat(flat: Boolean): IMarkerOptions

    fun anchor(anchorU: Float, anchorV: Float): IMarkerOptions

    fun infoWindowAnchor(infoWindowAnchorU: Float, infoWindowAnchorV: Float): IMarkerOptions

}
