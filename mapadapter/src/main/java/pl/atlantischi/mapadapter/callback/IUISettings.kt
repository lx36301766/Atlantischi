package pl.atlantischi.mapadapter.callback

/**
 * Created on 08/06/2018.

 * @author lx
 */
interface IUISettings {

    var isZoomControlsEnabled: Boolean

    var isCompassEnabled: Boolean

    var isMyLocationButtonEnabled: Boolean

    var isScrollGesturesEnabled: Boolean

    var isZoomGesturesEnabled: Boolean

    var isTiltGesturesEnabled: Boolean

    var isRotateGesturesEnabled: Boolean

    fun setAllGesturesEnabled(enable: Boolean)


    //********************** just support Gaode *******************************//

    var isIndoorSwitchEnabled: Boolean

    var isScaleControlsEnabled: Boolean

    var logoPosition: Int

    var zoomPosition: Int

    var isGestureScaleByMapCenter: Boolean

}

