package com.jm.jiedian.mapadapter.priv.gaode

import com.jm.jiedian.mapadapter.MapAdapter
import com.jm.jiedian.mapadapter.common.graphics.IMarker

/**
 * Created on 19/06/2018.

 * @author lx
 */

interface IGaodeMapAdapter : MapAdapter {

    override val mapObjectFactory: IGaodeMapObjectFactory

    fun setAMapGestureListener(listener: IGaodeAMapGestureListener)

    fun setMyLocationStyle(gaodeMyLocationStyle: IGaodeMyLocationStyle)

    fun setMapCustomEnable(enable: Boolean)

    fun setCustomMapStylePath(path: String)

    fun getMapScreenMarkers(): List<IMarker>

}
