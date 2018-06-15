package pl.atlantischi.mapadapter.internal.gaode.delegate

import com.amap.api.maps.model.LatLngBounds
import pl.atlantischi.mapadapter.callback.ILatLng
import pl.atlantischi.mapadapter.callback.ILatLngBounds

/**
 * Created on 14/06/2018.
 *
 * @author lx
 */

class GaodeLatLngBounds(val latLngBounds: LatLngBounds) : ILatLngBounds {

    override fun contains(latlng: ILatLng): Boolean {
        if (latlng is GaodeLatLng) {
            return latLngBounds.contains(latlng.latlng)
        }
        return false
    }

    override fun including(latlng: ILatLng): ILatLngBounds {
        if (latlng is GaodeLatLng) {
            latLngBounds.including(latlng.latlng)
        }
        return this
    }

    class Builder(private val builder: LatLngBounds.Builder) : ILatLngBounds.Builder {

        override fun include(latlng: ILatLng): ILatLngBounds.Builder {
            if (latlng is GaodeLatLng) {
                builder.include(latlng.latlng)
            }
            return this
        }

        override fun build(): ILatLngBounds {
            return GaodeLatLngBounds(builder.build())
        }

    }

}

