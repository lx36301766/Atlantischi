package pl.atlantischi.mapadapter.internal.google.delegate

import com.google.android.gms.maps.model.LatLng
import pl.atlantischi.mapadapter.common_api.ILatLng

/**
 * Created on 11/06/2018.

 * @author lx
 */

internal class GoogleLatLng(val latlng: LatLng) : ILatLng {

    override val latitude = latlng.latitude

    override val longitude = latlng.longitude

}
