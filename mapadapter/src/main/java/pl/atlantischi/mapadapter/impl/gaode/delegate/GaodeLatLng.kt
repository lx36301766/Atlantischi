package pl.atlantischi.mapadapter.impl.gaode.delegate

import com.amap.api.maps.model.LatLng
import pl.atlantischi.mapadapter.iiterface.ILatLng

/**
 * Created on 11/06/2018.

 * @author lx
 */

class GaodeLatLngDelegate(private val latlng: LatLng) : ILatLng {

    override val latitude: Double
        get() = latlng.latitude

    override val longitude: Double
        get() = latlng.longitude

}
