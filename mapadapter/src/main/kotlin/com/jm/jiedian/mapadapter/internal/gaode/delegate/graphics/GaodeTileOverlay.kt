package com.jm.jiedian.mapadapter.internal.gaode.delegate.graphics

import com.amap.api.maps.model.TileOverlay
import com.amap.api.maps.model.TileOverlayOptions
import com.jm.jiedian.mapadapter.mapapi.graphics.ITileOverlay

/**
 * Created on 19/06/2018.

 * @author lx
 */

internal class GaodeTileOverlay(private val tileOverlay: TileOverlay) : ITileOverlay {

    internal class Options : ITileOverlay.Options {

        val options = TileOverlayOptions()

    }

}