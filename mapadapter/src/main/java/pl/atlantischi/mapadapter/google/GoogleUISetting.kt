package pl.atlantischi.mapadapter.google

import com.google.android.gms.maps.UiSettings
import pl.atlantischi.mapadapter.IUISettings

/**
 * Created on 08/06/2018.

 * @author lx
 */
internal class GoogleUISetting(var uiSettings: UiSettings) : IUISettings {

    override var isZoomControlsEnabled = uiSettings.isZoomControlsEnabled
        set(value) {
            uiSettings.isZoomControlsEnabled = value
        }

    override var isCompassEnabled = uiSettings.isCompassEnabled
        set(value) {
            uiSettings.isCompassEnabled = value
        }

    override var isMyLocationButtonEnabled = uiSettings.isMyLocationButtonEnabled
        set(value) {
            uiSettings.isMyLocationButtonEnabled = value
        }

    override var isScrollGesturesEnabled = uiSettings.isScrollGesturesEnabled
        set(value) {
            uiSettings.isScrollGesturesEnabled = value
        }

    override var isZoomGesturesEnabled = uiSettings.isZoomGesturesEnabled
        set(value) {
            uiSettings.isZoomGesturesEnabled = value
        }

    override var isTiltGesturesEnabled = uiSettings.isTiltGesturesEnabled
        set(value) {
            uiSettings.isTiltGesturesEnabled = value
        }

    override var isRotateGesturesEnabled = uiSettings.isRotateGesturesEnabled
        set(value) {
            uiSettings.isRotateGesturesEnabled = value
        }

    override fun setAllGesturesEnabled(enable: Boolean) {
        uiSettings.setAllGesturesEnabled(enable)
    }



    //not support
    override var isIndoorSwitchEnabled = false

    //not support
    override var isScaleControlsEnabled = false

    //not support
    override var logoPosition = 0

    //not support
    override var zoomPosition = 0

    //not support
    override var isGestureScaleByMapCenter = false

}
