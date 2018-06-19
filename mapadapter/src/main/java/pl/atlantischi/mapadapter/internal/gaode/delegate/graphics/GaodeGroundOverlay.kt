package pl.atlantischi.mapadapter.internal.gaode.delegate.graphics

import com.amap.api.maps.model.GroundOverlay
import com.amap.api.maps.model.GroundOverlayOptions
import pl.atlantischi.mapadapter.callback.graphics.IGroundOverlay

/**
 * Created on 19/06/2018.

 * @author lx
 */
class GaodeGroundOverlay(private val groundOverlay: GroundOverlay) : IGroundOverlay {


    class Options : IGroundOverlay.Options {

        val options = GroundOverlayOptions()

    }

}