package com.jm.jiedian.mapadapter.internal.google.delegate

import com.google.android.gms.maps.model.CameraPosition
import com.jm.jiedian.mapadapter.mapapi.ICameraPosition
import com.jm.jiedian.mapadapter.mapapi.ILatLng

/**
 * Created on 11/06/2018.

 * @author lx
 */

internal class GoogleCameraPosition(val cameraPosition: CameraPosition) : ICameraPosition {

    override val target = GoogleLatLng(cameraPosition.target)

    override val zoom = cameraPosition.zoom

    override val tilt = cameraPosition.tilt

    override val bearing = cameraPosition.bearing

    internal class Builder : ICameraPosition.Builder {

        private val builder = CameraPosition.Builder()

        override fun target(latlng: ILatLng): ICameraPosition.Builder {
            if (latlng is GoogleLatLng) {
                builder.target(latlng.latlng)
            }
            return this
        }

        override fun zoom(zoom: Float): ICameraPosition.Builder {
            builder.zoom(zoom)
            return this
        }

        override fun tilt(tilt: Float): ICameraPosition.Builder {
            builder.tilt(tilt)
            return this
        }

        override fun bearing(bearing: Float): ICameraPosition.Builder {
            builder.bearing(bearing)
            return this
        }

        override fun build(): ICameraPosition {
            return GoogleCameraPosition(builder.build())
        }

    }

}
