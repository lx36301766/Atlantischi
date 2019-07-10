package com.jm.gaodemap.priv.location

import com.amap.api.location.AMapLocation
import com.jm.jiedian.mapadapter.priv.gaode.location.IGaodeAMapLocation

/**
 * Created on 21/06/2018.

 * @author lx
 */

internal class GaodeAMapLocation(val aMapLocation: AMapLocation) : IGaodeAMapLocation {

    override var latitude: Double
        get() = aMapLocation.latitude
        set(value) {
            aMapLocation.latitude = value
        }

    override var longitude: Double
        get() = aMapLocation.longitude
        set(value) {
            aMapLocation.longitude = value
        }

}
