package pl.atlantischi.mapadapter.internal.gaode.delegate

import com.amap.api.maps.model.Marker
import pl.atlantischi.mapadapter.callback.IMarker

/**
 * Created on 11/06/2018.

 * @author lx
 */

class GaodeMarker(private val marker: Marker): IMarker {

    override var snippet: String
        set(value) {
            marker.snippet = value
        }
        get() = marker.snippet

}
