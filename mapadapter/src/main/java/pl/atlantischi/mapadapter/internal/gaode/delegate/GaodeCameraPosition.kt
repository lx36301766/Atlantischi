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

}
