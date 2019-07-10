package com.jm.jiedian.mapadapter.priv.gaode.location

/**
 * Created on 21/06/2018.

 * @author lx
 */

interface IGaodeAMapLocationClient {

    fun setLocationListener(locationListener: (aMapLocation: IGaodeAMapLocation) -> Unit)

    fun setLocationOption(option: IGaodeAMapLocationClientOption)

    fun isStarted(): Boolean

    fun startLocation()

    fun stopLocation()

    fun onDestroy()
}
