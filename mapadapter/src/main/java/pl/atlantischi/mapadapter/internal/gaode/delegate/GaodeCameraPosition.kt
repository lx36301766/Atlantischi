package pl.atlantischi.mapadapter.internal.gaode.delegate

import com.amap.api.maps.model.CameraPosition
import pl.atlantischi.mapadapter.callback.ICameraPosition
import pl.atlantischi.mapadapter.callback.ILatLng

/**
 * Created on 11/06/2018.

 * @author lx
 */

class GaodeCameraPosition(private val cameraPosition: CameraPosition) : ICameraPosition {

    override val target: ILatLng
        get() = GaodeLatLng(cameraPosition.target)

    override val zoom: Float
        get() = cameraPosition.zoom

    override val tilt: Float
        get() = cameraPosition.tilt

    override val bearing: Float
        get() = cameraPosition.bearing

}
