package com.jm.gaodemap.priv.location

import com.amap.api.location.AMapLocationClientOption
import com.jm.jiedian.mapadapter.priv.gaode.location.IGaodeAMapLocationClientOption

/**
 * Created on 21/06/2018.

 * @author lx
 */

internal class GaodeAMapLocationClientOption(val option: AMapLocationClientOption) : IGaodeAMapLocationClientOption {

    override var locationMode: IGaodeAMapLocationClientOption.IGaodeAMapLocationMode
        get() {
            return when(option.locationMode) {
                AMapLocationClientOption.AMapLocationMode.Battery_Saving -> IGaodeAMapLocationClientOption.IGaodeAMapLocationMode.Battery_Saving
                AMapLocationClientOption.AMapLocationMode.Hight_Accuracy -> IGaodeAMapLocationClientOption.IGaodeAMapLocationMode.Hight_Accuracy
                AMapLocationClientOption.AMapLocationMode.Device_Sensors -> IGaodeAMapLocationClientOption.IGaodeAMapLocationMode.Device_Sensors
            }
        }
        set(value) {
            option.locationMode = when(value) {
                IGaodeAMapLocationClientOption.IGaodeAMapLocationMode.Battery_Saving -> AMapLocationClientOption.AMapLocationMode.Battery_Saving
                IGaodeAMapLocationClientOption.IGaodeAMapLocationMode.Hight_Accuracy -> AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
                IGaodeAMapLocationClientOption.IGaodeAMapLocationMode.Device_Sensors -> AMapLocationClientOption.AMapLocationMode.Device_Sensors
            }
        }

    override var isOnceLocation: Boolean
        get() = option.isOnceLocation
        set(value) {
            option.isOnceLocation = value
        }

}
