package pl.atlantischi.mapadapter.internal.google.delegate

import com.google.android.gms.maps.model.Marker
import pl.atlantischi.mapadapter.callback.IMarker

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
