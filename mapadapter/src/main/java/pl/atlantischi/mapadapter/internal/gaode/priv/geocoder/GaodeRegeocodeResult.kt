package pl.atlantischi.mapadapter.internal.gaode.priv.geocoder

import com.amap.api.services.geocoder.RegeocodeResult
import pl.atlantischi.mapadapter.separate.gaode.geocoder.IGaodeRegeocodeResult

/**
 * Created on 21/06/2018.

 * @author lx
 */

internal class GaodeRegeocodeResult(val regeocodeResult: RegeocodeResult) : IGaodeRegeocodeResult {

    override fun getCity(): String {
        return regeocodeResult.regeocodeAddress.city
    }

}
