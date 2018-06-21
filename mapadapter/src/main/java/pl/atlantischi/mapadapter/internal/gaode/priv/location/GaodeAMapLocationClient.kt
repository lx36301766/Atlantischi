package pl.atlantischi.mapadapter.internal.gaode.priv.location

import com.amap.api.location.AMapLocationClient
import pl.atlantischi.mapadapter.separate.gaode.location.IGaodeAMapLocation
import pl.atlantischi.mapadapter.separate.gaode.location.IGaodeAMapLocationClient
import pl.atlantischi.mapadapter.separate.gaode.location.IGaodeAMapLocationClientOption

/**
 * Created on 21/06/2018.

 * @author lx
 */

internal class GaodeAMapLocationClient(val client: AMapLocationClient) : IGaodeAMapLocationClient {

    override fun setLocationListener(locationListener: (aMapLocation: IGaodeAMapLocation) -> Unit) {
        client.setLocationListener {
            locationListener.invoke(GaodeAMapLocation(it))
        }
    }

    override fun setLocationOption(option: IGaodeAMapLocationClientOption) {
        val glco = option as GaodeAMapLocationClientOption
        client.setLocationOption(glco.option)
    }

    override fun isStarted(): Boolean {
        return client.isStarted
    }

    override fun startLocation() {
        client.startLocation()
    }

    override fun stopLocation() {
        client.stopLocation()
    }

    override fun onDestroy() {
        client.onDestroy()
    }

}
