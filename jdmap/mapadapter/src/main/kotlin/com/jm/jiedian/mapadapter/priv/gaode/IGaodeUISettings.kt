package com.jm.jiedian.mapadapter.priv.gaode

import com.jm.jiedian.mapadapter.common.IUISettings

/**
 * Created on 08/06/2018.

 * @author lx
 */

interface IGaodeUISettings : IUISettings {

    var isIndoorSwitchEnabled: Boolean

    var isScaleControlsEnabled: Boolean

    var logoPosition: Int

    var zoomPosition: Int

    var isGestureScaleByMapCenter: Boolean

    fun setZoomInByScreenCenter(zoomIn: Boolean)

}
