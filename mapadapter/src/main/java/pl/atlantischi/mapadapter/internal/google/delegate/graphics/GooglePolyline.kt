package pl.atlantischi.mapadapter.internal.google.delegate.graphics

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import pl.atlantischi.mapadapter.callback.ILatLng
import pl.atlantischi.mapadapter.callback.IPolyline
import pl.atlantischi.mapadapter.internal.google.delegate.GoogleLatLng

/**
 * Created on 19/06/2018.

 * @author lx
 */

class GooglePolyline(private val polyline: Polyline) : IPolyline {

    override fun remove() {
        polyline.remove()
    }

    override var id = polyline.id

    override var points: List<ILatLng>
        get() {
            val list = mutableListOf<ILatLng>()
            for (point in polyline.points) {
                list.add(GoogleLatLng(point))
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

}
