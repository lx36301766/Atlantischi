package com.jm.jiedian.mapadapter.internal.gaode.priv.route

import com.amap.api.services.core.LatLonPoint
import com.jm.jiedian.mapadapter.separate.gaode.route.IGaodeWalkStep
import com.amap.api.services.route.WalkStep
import com.jm.jiedian.mapadapter.internal.gaode.priv.GaodeLatLonPoint
import com.jm.jiedian.mapadapter.separate.gaode.IGaodeLatLonPoint

/**
 * Created on 22/06/2018.

 * @author lx
 */

internal class GaodeWalkStep(val step : WalkStep) : IGaodeWalkStep {

    override var polyline: List<IGaodeLatLonPoint>
        get() {
            val list = mutableListOf<IGaodeLatLonPoint>()
            step.polyline.forEach { list.add(GaodeLatLonPoint(it)) }
            return list
        }
        set(value) {
            val list = mutableListOf<LatLonPoint>()
            value.forEach {
                val point = it as GaodeLatLonPoint
                list.add(point.latLonPoint)
            }
            step.polyline = list
        }

}
