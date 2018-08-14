package pl.atlantischi.mapadapter.impl.google.delegate

import com.google.android.gms.maps.model.CameraPosition
import pl.atlantischi.mapadapter.iiterface.ICameraPosition
import pl.atlantischi.mapadapter.iiterface.ILatLng

/**
 * Created on 11/06/2018.

 * @author lx
 */

class GoogleCameraPositionDelegate(private val cameraPosition: CameraPosition) : ICameraPosition {

    override val target: ILatLng
        get() = GoogleLatLngDelegate(cameraPosition.target)

    override val zoom: Float
        get() = cameraPosition.zoom

    override val tilt: Float
        get() = cameraPosition.tilt

    override val bearing: Float
        get() = cameraPosition.bearing

}
