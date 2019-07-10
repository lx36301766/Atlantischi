package com.jm.gaodemap.common.graphics

import com.amap.api.maps.model.GroundOverlay
import com.amap.api.maps.model.GroundOverlayOptions
import com.jm.jiedian.mapadapter.common.graphics.IGroundOverlay

/**
 * Created on 19/06/2018.

 * @author lx
 */

internal class GaodeGroundOverlay(private val groundOverlay: GroundOverlay) : IGroundOverlay {


    internal class Options : IGroundOverlay.Options {

        val options = GroundOverlayOptions()

    }

}