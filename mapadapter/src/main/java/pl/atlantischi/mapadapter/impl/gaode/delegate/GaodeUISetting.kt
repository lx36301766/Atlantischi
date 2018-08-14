package pl.atlantischi.mapadapter.impl.gaode

import com.amap.api.maps.UiSettings
import pl.atlantischi.mapadapter.iiterface.IUISettings

/**
 * Created on 08/06/2018.

 * @author lx
 */
internal class GaodeUISetting (var uiSettings: UiSettings) : IUISettings {

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



    override var isIndoorSwitchEnabled = uiSettings.isIndoorSwitchEnabled
        set(value) {
            uiSettings.isIndoorSwitchEnabled = value
        }

    override var isScaleControlsEnabled = uiSettings.isScaleControlsEnabled
        set(value) {
            uiSettings.isScaleControlsEnabled = value
        }

    override var logoPosition = uiSettings.logoPosition
        set(value) {
            uiSettings.logoPosition = value
        }

    override var zoomPosition = uiSettings.zoomPosition
        set(value) {
            uiSettings.zoomPosition = value
        }

    override var isGestureScaleByMapCenter = uiSettings.isGestureScaleByMapCenter
        set(value) {
            uiSettings.isGestureScaleByMapCenter = value
        }

}