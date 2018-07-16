package com.jm.jiedian.mapadapter.internal.gaode.delegate

import com.amap.api.maps.model.LatLng
import com.jm.jiedian.mapadapter.mapapi.ILatLng

/**
 * Created on 11/06/2018.

 * @author lx
 */

internal class GaodeLatLng(val latlng: LatLng) : ILatLng {

    override val latitude = latlng.latitude

    override val longitude = latlng.longitude

}
