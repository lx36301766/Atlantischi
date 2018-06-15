package pl.atlantischi.mapadapter.internal.google.delegate

import com.google.android.gms.maps.model.CameraPosition
import pl.atlantischi.mapadapter.callback.ICameraPosition
import pl.atlantischi.mapadapter.callback.ILatLng

/**
 * Created on 11/06/2018.

 * @author lx
 */

internal class GoogleCameraPosition(val cameraPosition: CameraPosition) : ICameraPosition {

    override val target = GoogleLatLng(cameraPosition.target)

    override val zoom = cameraPosition.zoom

    override val tilt = cameraPosition.tilt

    override val bearing = cameraPosition.bearing

}
