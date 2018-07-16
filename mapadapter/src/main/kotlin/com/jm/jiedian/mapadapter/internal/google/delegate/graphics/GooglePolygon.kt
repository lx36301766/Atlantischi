package com.jm.jiedian.mapadapter.internal.google.delegate.graphics

import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions
import com.jm.jiedian.mapadapter.mapapi.graphics.IPolygon

/**
 * Created on 19/06/2018.

 * @author lx
 */

internal class GooglePolygon(private val polygon: Polygon) : IPolygon {

    internal class Options : IPolygon.Options {

        val options = PolygonOptions()

    }

}
