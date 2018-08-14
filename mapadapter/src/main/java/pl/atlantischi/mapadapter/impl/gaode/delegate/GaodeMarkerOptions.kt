package pl.atlantischi.mapadapter.impl.gaode.delegate

import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import pl.atlantischi.mapadapter.delegate.MarkerOptionsDelegate

/**
 * Created on 11/06/2018.

 * @author lx
 */

class GaodeMarkerOptionsDelegate(val delegate: MarkerOptionsDelegate): MarkerOptionsDelegate() {

    fun toMarkerOptions(): MarkerOptions {
        val options = MarkerOptions()
        delegate.position?.let {
            options.position(LatLng(it.latitude, it.longitude))
        }
        return options
    }

}
