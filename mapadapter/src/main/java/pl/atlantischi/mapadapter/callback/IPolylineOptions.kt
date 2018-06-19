package pl.atlantischi.mapadapter.callback

/**
 * Created on 11/06/2018.

 * @author lx
 */

interface IPolylineOptions {

    fun add(latlng: ILatLng): IPolylineOptions

    fun add(vararg latlng: ILatLng): IPolylineOptions

    fun addAll(latlng: Iterable<ILatLng>): IPolylineOptions

    fun width(width: Float): IPolylineOptions

    fun color(color: Int): IPolylineOptions

    fun zIndex(zIndex: Float): IPolylineOptions

    fun visible(visible: Boolean): IPolylineOptions

    fun geodesic(geodesic: Boolean): IPolylineOptions

    fun getPoints(): List<ILatLng>

}
