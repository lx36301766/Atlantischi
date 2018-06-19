package pl.atlantischi.mapadapter.internal.gaode.delegate

import com.amap.api.maps.model.CameraPosition
import pl.atlantischi.mapadapter.callback.ICameraPosition
import pl.atlantischi.mapadapter.callback.ILatLng

/**
 * Created on 11/06/2018.

 * @author lx
 */

internal class GaodeCameraPosition(val cameraPosition: CameraPosition) : ICameraPosition {

    override val target = GaodeLatLng(cameraPosition.target)

    override val zoom = cameraPosition.zoom

    override val tilt = cameraPosition.tilt

    override val bearing = cameraPosition.bearing

    class Builder : ICameraPosition.Builder {

        private val builder = CameraPosition.Builder()

        override fun target(latlng: ILatLng): ICameraPosition.Builder {
            if (latlng is GaodeLatLng) {
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
            return GaodeCameraPosition(builder.build())
        }

    }

}
