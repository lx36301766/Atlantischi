package com.jm.jiedian.mapadapter.internal.google.delegate.graphics

import com.google.android.gms.maps.model.TileOverlay
import com.google.android.gms.maps.model.TileOverlayOptions
import com.jm.jiedian.mapadapter.mapapi.graphics.ITileOverlay

/**
 * Created on 19/06/2018.

 * @author lx
 */

internal class GoogleTileOverlay(private val tileOverlay: TileOverlay) : ITileOverlay {

    internal class Options : ITileOverlay.Options {

        val options = TileOverlayOptions()

    }

}