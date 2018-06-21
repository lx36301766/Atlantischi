package pl.atlantischi.mapadapter.internal.gaode.priv

import com.amap.api.maps.AMapUtils
import pl.atlantischi.mapadapter.separate.gaode.IGaodeAMapUtils
import pl.atlantischi.mapadapter.internal.gaode.delegate.GaodeLatLng
import pl.atlantischi.mapadapter.mapapi.ILatLng

/**
 * Created on 19/06/2018.

 * @author lx
 */

internal class GaodeAMapUtils : IGaodeAMapUtils {

    override fun calculateLineDistance(src: ILatLng, dest: ILatLng) {
        val srcGll = src as GaodeLatLng
        val destGll = dest as GaodeLatLng
        AMapUtils.calculateLineDistance(srcGll.latlng, destGll.latlng)
    }

}
