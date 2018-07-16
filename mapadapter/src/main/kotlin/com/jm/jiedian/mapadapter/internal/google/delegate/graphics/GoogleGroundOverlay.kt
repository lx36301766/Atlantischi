package com.jm.jiedian.mapadapter.internal.google.delegate.graphics

import com.google.android.gms.maps.model.GroundOverlay
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.jm.jiedian.mapadapter.mapapi.graphics.IGroundOverlay

/**
 * Created on 19/06/2018.

 * @author lx
 */

internal class GoogleGroundOverlay(private val groundOverlay: GroundOverlay) : IGroundOverlay {

    internal class Options : IGroundOverlay.Options {

        val options = GroundOverlayOptions()

    }

}