package pl.atlantischi.mapadapter.internal.google.delegate

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import pl.atlantischi.mapadapter.params.MarkerOptionsParameters

/**
 * Created on 11/06/2018.

 * @author lx
 */

internal class GoogleMarkerOptions {

    companion object {

        fun build(parameters: MarkerOptionsParameters): MarkerOptions {
            val options = MarkerOptions()
            parameters.position?.let {
                options.position(LatLng(it.latitude, it.longitude))
            }
            return options
        }

    }

}
