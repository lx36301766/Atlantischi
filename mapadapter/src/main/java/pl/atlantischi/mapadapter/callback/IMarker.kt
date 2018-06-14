package pl.atlantischi.mapadapter.callback

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

}
