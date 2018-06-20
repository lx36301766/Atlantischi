package pl.atlantischi.mapadapter.separate.gaode

import pl.atlantischi.mapadapter.mapapi.IUISettings

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

}
