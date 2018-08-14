package pl.atlantischi.mapadapter.impl.google

import com.google.android.gms.maps.model.Marker
import pl.atlantischi.mapadapter.response.IMarker

/**
 * Created on 11/06/2018.

 * @author lx
 */

class GoogleMarker(private val marker: Marker): IMarker {

    override var snippet: String
        set(value) {
            marker.snippet = value
        }
        get() = marker.snippet

}
