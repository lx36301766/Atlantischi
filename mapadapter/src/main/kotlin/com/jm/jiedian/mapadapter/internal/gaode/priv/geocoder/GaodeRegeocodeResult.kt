package com.jm.jiedian.mapadapter.internal.gaode.priv.geocoder

import com.amap.api.services.geocoder.RegeocodeResult
import com.jm.jiedian.mapadapter.separate.gaode.geocoder.IGaodeRegeocodeResult

/**
 * Created on 21/06/2018.

 * @author lx
 */

internal class GaodeRegeocodeResult(val regeocodeResult: RegeocodeResult) : IGaodeRegeocodeResult {

    override val city: String
        get() = regeocodeResult.regeocodeAddress.city

}
