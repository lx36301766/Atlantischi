package pl.atlantischi.mapadapter.internal.google.delegate

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import pl.atlantischi.mapadapter.params.MarkerOptionsDelegate

/**
 * Created on 11/06/2018.

 * @author lx
 */

class GoogleMarkerOptions {

    companion object {

        fun build(delegate: MarkerOptionsDelegate): MarkerOptions {
            val options = MarkerOptions()
            delegate.position?.let {
                options.position(LatLng(it.latitude, it.longitude))
            }
            return options
        }

    }

}
