package com.jm.jiedian.mapadapter.internal.gaode.delegate.graphics

import com.amap.api.maps.model.Polygon
import com.amap.api.maps.model.PolygonOptions
import com.jm.jiedian.mapadapter.mapapi.graphics.IPolygon

/**
 * Created on 19/06/2018.

 * @author lx
 */

internal class GaodePolygon(private val polygon: Polygon) : IPolygon {


    internal class Options : IPolygon.Options {

        val options = PolygonOptions()

    }

}