package com.jm.jiedian.mapadapter.internal.google.delegate

import com.google.android.gms.maps.model.LatLngBounds
import com.jm.jiedian.mapadapter.mapapi.ILatLng
import com.jm.jiedian.mapadapter.mapapi.ILatLngBounds

/**
 * Created on 14/06/2018.
 *
 * @author lx
 */

internal class GoogleLatLngBounds(val latLngBounds: LatLngBounds) : ILatLngBounds {

    override fun contains(latlng: ILatLng): Boolean {
        if (latlng is GoogleLatLng) {
            return latLngBounds.contains(latlng.latlng)
        }
        return false
    }

    override fun including(latlng: ILatLng): ILatLngBounds {
        if (latlng is GoogleLatLng) {
            latLngBounds.including(latlng.latlng)
        }
        return this
    }

    internal class Builder : ILatLngBounds.Builder {

        private val builder = LatLngBounds.Builder()

        override fun include(latlng: ILatLng): Builder {
            if (latlng is GoogleLatLng) {
                builder.include(latlng.latlng)
            }
            return this
        }

        override fun build(): ILatLngBounds {
            return GoogleLatLngBounds(builder.build())
        }

    }

}

