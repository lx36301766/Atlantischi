package com.jm.gaodemap.priv

import com.amap.api.services.core.LatLonPoint
import com.jm.jiedian.mapadapter.priv.gaode.IGaodeLatLonPoint

/**
 * Created on 19/06/2018.

 * @author lx
 */

internal class GaodeLatLonPoint(val latLonPoint: LatLonPoint) : IGaodeLatLonPoint {

    override var latitude: Double
        get() = latLonPoint.latitude
        set(value) {
            latLonPoint.latitude = value
        }

    override var longitude: Double
        get() = latLonPoint.longitude
        set(value) {
            latLonPoint.longitude = value
        }

}
