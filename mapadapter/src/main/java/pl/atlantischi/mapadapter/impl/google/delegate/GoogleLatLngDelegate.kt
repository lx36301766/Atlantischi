package pl.atlantischi.mapadapter.impl.google.wrapper

import com.google.android.gms.maps.model.LatLng
import pl.atlantischi.mapadapter.delegate.ILatLng

/**
 * Created on 11/06/2018.

 * @author lx
 */

class GoogleLatLngDelegate(private val latlng: LatLng) : ILatLng {

    override val latitude: Double
        get() = latlng.latitude

    override val longitude: Double
        get() = latlng.longitude

}