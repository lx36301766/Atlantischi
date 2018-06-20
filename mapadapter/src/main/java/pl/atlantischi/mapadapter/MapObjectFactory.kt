package pl.atlantischi.mapadapter

import pl.atlantischi.mapadapter.common_api.*
import pl.atlantischi.mapadapter.common_api.graphics.*

/**
 * Created on 15/06/2018.

 * @author lx
 */

interface MapObjectFactory {

    val uiSetting: IUISettings

    val bitmapDescriptorFactory: IBitmapDescriptorFactory

    val cameraUpdateFactory: ICameraUpdateFactory

    fun newLatlng(latitude: Double, longitude: Double): ILatLng

    fun newCameraPositionBuilder(): ICameraPosition.Builder

    fun newLatLngBoundBuilder(): ILatLngBounds.Builder

    fun newPolylineOptions(): IPolyline.Options

    fun newPolygonOptions(): IPolygon.Options

    fun newCircleOptions(): ICircle.Options

    fun newMarkerOptions(): IMarker.Options

    fun newGroundOverlayOptions(): IGroundOverlay.Options

    fun newTileOverlayOptions(): ITileOverlay.Options

}
