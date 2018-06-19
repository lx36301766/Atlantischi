package pl.atlantischi.mapadapter

import pl.atlantischi.mapadapter.callback.*

/**
 * Created on 15/06/2018.

 * @author lx
 */

interface MapObjectFactory {

    val uiSetting: IUISettings

    val bitmapDescriptorFactory: IBitmapDescriptorFactory

    val cameraUpdateFactory: ICameraUpdateFactory

    val cameraPosition: ICameraPosition

    fun newLatlng(latitude: Double, longitude: Double): ILatLng

    fun newLatLngBoundBuilder(): ILatLngBounds.Builder

    fun newMarkerOptions(): IMarkerOptions

}
