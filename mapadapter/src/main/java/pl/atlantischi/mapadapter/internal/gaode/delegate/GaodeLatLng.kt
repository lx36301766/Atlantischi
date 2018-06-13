package pl.atlantischi.mapadapter.internal.gaode.delegate

import com.amap.api.maps.model.LatLng
import pl.atlantischi.mapadapter.callback.ILatLng

/**
 * Created on 11/06/2018.

 * @author lx
 */

internal class GaodeLatLng(private val latlng: LatLng) : ILatLng {

    override val latitude: Double
        get() = latlng.latitude

    override val longitude: Double
        get() = latlng.longitude

}
