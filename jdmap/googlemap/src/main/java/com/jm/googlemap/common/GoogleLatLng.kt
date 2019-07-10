package com.jm.googlemap.common

import com.google.android.gms.maps.model.LatLng
import com.jm.jiedian.mapadapter.common.ILatLng

/**
 * Created on 11/06/2018.

 * @author lx
 */

internal class GoogleLatLng(val latlng: LatLng) : ILatLng {

    override val latitude = latlng.latitude

    override val longitude = latlng.longitude

}
