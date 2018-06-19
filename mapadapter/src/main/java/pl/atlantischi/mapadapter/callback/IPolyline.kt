package pl.atlantischi.mapadapter.callback

/**
 * Created on 11/06/2018.

 * @author lx
 */

interface IPolyline {

    fun remove()

    val id: String

    var points: List<ILatLng>

    var width: Float

    var color: Int

    var zIndex: Float

    var isVisible: Boolean

    var isGeodesic: Boolean

}
