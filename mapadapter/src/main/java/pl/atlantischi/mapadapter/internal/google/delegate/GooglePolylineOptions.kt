package pl.atlantischi.mapadapter.internal.google.delegate

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import pl.atlantischi.mapadapter.callback.ILatLng
import pl.atlantischi.mapadapter.callback.IPolylineOptions

/**
 * Created on 19/06/2018.

 * @author lx
 */

class GooglePolylineOptions : IPolylineOptions {

    val options = PolylineOptions()

    override fun add(latlng: ILatLng): IPolylineOptions {
        val gll = latlng as GoogleLatLng
        options.add(gll.latlng)
        return this
    }

    override fun add(vararg latlng: ILatLng): IPolylineOptions {
        val llArray = Array(latlng.size, { LatLng(latlng[it].latitude, latlng[it].longitude) })
        options.add(*llArray)
        return this
    }

    override fun addAll(latlng: Iterable<ILatLng>): IPolylineOptions {
        val llList = mutableListOf<LatLng>()
        for (iLatLng in latlng) {
            llList.add(LatLng(iLatLng.latitude, iLatLng.longitude))
        }
        options.addAll(llList)
        return this
    }

    override fun width(width: Float): IPolylineOptions {
        options.width(width)
        return this
    }

    override fun color(color: Int): IPolylineOptions {
        options.color(color)
        return this
    }

    override fun zIndex(zIndex: Float): IPolylineOptions {
        options.zIndex(zIndex)
        return this
    }

    override fun visible(visible: Boolean): IPolylineOptions {
        options.visible(visible)
        return this
    }

    override fun geodesic(geodesic: Boolean): IPolylineOptions {
        options.geodesic(geodesic)
        return this
    }

    override fun getPoints(): List<ILatLng> {
        val list = mutableListOf<ILatLng>()
        for (point in options.points) {
            list.add(GoogleLatLng(point))
        }
        return list
    }

}
