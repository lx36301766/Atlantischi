package pl.atlantischi.mapadapter.internal.gaode.delegate.graphics

import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Polyline
import com.amap.api.maps.model.PolylineOptions
import pl.atlantischi.mapadapter.callback.ILatLng
import pl.atlantischi.mapadapter.callback.graphics.IPolyline
import pl.atlantischi.mapadapter.internal.gaode.delegate.GaodeLatLng

/**
 * Created on 19/06/2018.

 * @author lx
 */

internal class GaodePolyline(private val polyline: Polyline) : IPolyline {

    override fun remove() {
        polyline.remove()
    }

    override var id = polyline.id

    override var points: List<ILatLng>
        get() {
            val list = mutableListOf<ILatLng>()
            for (point in polyline.points) {
                list.add(GaodeLatLng(point))
            }
            return list
        }
        set(value) {
            val list = mutableListOf<LatLng>()
            for (iLatLng in value) {
                list.add(LatLng(iLatLng.latitude, iLatLng.longitude))
            }
            polyline.points = list
        }

    override var width = polyline.width
        set(value) {
            polyline.width = value
        }

    override var color = polyline.color
        set(value) {
            polyline.color = value
        }

    override var zIndex = polyline.zIndex
        set(value) {
            polyline.zIndex = value
        }

    override var isVisible = polyline.isVisible
        set(value) {
            polyline.isVisible = value
        }

    override var isGeodesic = polyline.isGeodesic
        set(value) {
            polyline.isGeodesic = value
        }



    internal class Options : IPolyline.Options {

        val options = PolylineOptions()

        override fun add(latlng: ILatLng): IPolyline.Options {
            val gll = latlng as GaodeLatLng
            options.add(gll.latlng)
            return this
        }

        override fun add(vararg latlng: ILatLng): IPolyline.Options {
            val llArray = Array(latlng.size, { LatLng(latlng[it].latitude, latlng[it].longitude) })
            options.add(*llArray)
            return this
        }

        override fun addAll(latlng: Iterable<ILatLng>): IPolyline.Options {
            val llList = mutableListOf<LatLng>()
            for (iLatLng in latlng) {
                llList.add(LatLng(iLatLng.latitude, iLatLng.longitude))
            }
            options.addAll(llList)
            return this
        }

        override fun width(width: Float): IPolyline.Options {
            options.width(width)
            return this
        }

        override fun color(color: Int): IPolyline.Options {
            options.color(color)
            return this
        }

        override fun zIndex(zIndex: Float): IPolyline.Options {
            options.zIndex(zIndex)
            return this
        }

        override fun visible(visible: Boolean): IPolyline.Options {
            options.visible(visible)
            return this
        }

        override fun geodesic(geodesic: Boolean): IPolyline.Options {
            options.geodesic(geodesic)
            return this
        }

        override fun getPoints(): List<ILatLng> {
            val list = mutableListOf<ILatLng>()
            for (point in options.points) {
                list.add(GaodeLatLng(point))
            }
            return list
        }

    }

}
