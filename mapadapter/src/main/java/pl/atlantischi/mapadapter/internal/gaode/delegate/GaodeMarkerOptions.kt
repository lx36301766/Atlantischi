package pl.atlantischi.mapadapter.internal.gaode.delegate

import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import pl.atlantischi.mapadapter.params.MarkerOptionsDelegate

/**
 * Created on 11/06/2018.

 * @author lx
 */

class GaodeMarkerOptions {

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
