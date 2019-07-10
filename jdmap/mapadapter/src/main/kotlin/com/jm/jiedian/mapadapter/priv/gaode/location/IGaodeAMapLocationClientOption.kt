package com.jm.jiedian.mapadapter.priv.gaode.location

/**
 * Created on 21/06/2018.

 * @author lx
 */

interface IGaodeAMapLocationClientOption {

    var locationMode: IGaodeAMapLocationMode

    var isOnceLocation: Boolean

    enum class IGaodeAMapLocationMode {
        Battery_Saving,
        Device_Sensors,
        Hight_Accuracy;
    }

}
