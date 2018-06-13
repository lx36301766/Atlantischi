package pl.atlantischi.mapadapter.internal.gaode.delegate

import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import pl.atlantischi.mapadapter.params.MarkerOptionsParameters

/**
 * Created on 11/06/2018.

 * @author lx
 */

internal class GaodeMarkerOptions {

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
